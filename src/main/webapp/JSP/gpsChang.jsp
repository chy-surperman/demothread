<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
    <title>批量逆地理编码</title>
    <link rel="stylesheet" href="https://a.amap.com/jsapi_demos/static/demo-center/css/demo-center.css"/>
    <style>
        html, body, #container {
            height: 100%;
            width: 100%;
        }

        .btn {
            width: 7rem;
            margin-left: 1.0rem;
        }

        .input-item-text {
            background-color: white;
            padding-left: 4px;
            width: 12rem;
        }

        .mark {
            width: 19px;
            height: 31px;
            color: white;
            text-align: center;
            line-height: 21px;
            background: url('https://webapi.amap.com/theme/v1.3/markers/n/mark_b.png');
        }
    </style>
</head>
<body>
<div id="container"></div>
<div class="input-card" style='width:36rem;'>
    <h4 style='color:grey'>经纬度批量处理</h4>
    <div id="postions">
        <div id='blank_item' class="input-item">
            <div class="input-item-prepend"><span class="input-item-text">公交线路</span></div>
            <input id='BusLineName' type="text" value='218'>
        </div>
    </div>
    <div class="input-item">
        <input id="regeo" type="button" class="btn" value="经纬度->gps"/>
        <input id="clear" type="button" class="btn" value="清除"/>
        <input id="search" type="button" class="btn" value="查询"/>
        <input id="save" type="button" class="btn" value="保存"/>
    </div>
</div>
<script type="text/javascript"
        src="https://webapi.amap.com/maps?v=1.4.15&key=b4e288825a07a480ca57cd9113411feb&plugin=AMap.Geocoder&&plugin=AMap.LineSearch"></script>
<script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript">
var map = new AMap.Map("container", {
    resizeEnable: true
});
var posDiv = document.getElementById('postions');

var lnglats = [], markers = [];
map.on('click', function (e) {
    if (lnglats.length <10000) {
        lnglats.push(e.lnglat);
        var index = lnglats.length;
        var marker = new AMap.Marker({
            content: '<div class="mark">' + lnglats.length + '</div>',
            position: e.lnglat
        });
        markers.push(marker);
        map.add(marker);

        var newItem =
            '<div class="input-item">' +
            '<div class="input-item-prepend"><span class="input-item-text" >' + e.lnglat + '</span></div>' +
            '<input id="address' + index + '" disabled type="text">' +
            '</div>';
        document.getElementById('blank_item').insertAdjacentHTML('beforebegin', newItem)
    }

})
var geocoder;

function regeoCode() {
    if (!geocoder) {
        geocoder = new AMap.Geocoder({
            city: "010", //城市设为北京，默认：“全国”
            radius: 1000 //范围，默认：500
        });
    }
    geocoder.getAddress(lnglats, function (status, result) {
        var address = []
        if (status === 'complete' && result.regeocodes.length) {
            for (var i = 0; i < result.regeocodes.length; i += 1) {
                document.getElementById("address" + (i + 1)).value = result.regeocodes[i].formattedAddress
            }

        } else {
            alert(JSON.stringify(result))
        }
    });
}

function clear() {
    map.remove(markers);
    markers = [];
    lnglats = []
    posDiv.innerHTML = '<div id="blank_item" class="input-item"><div class="input-item-prepend"><span class="input-item-text">公交线路</span></div><input  type="text" id="BusLineName" value="218"></div>';
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/web/fence/dellnglat",
        contentType: "application/json; charset=utf-8",
        success: function(msg){
            console.log( "Data Saved: " + "保存成功" );
        }
    })
}
function save() {
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/web/fence/lnglat",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(lnglats),
        success: function(msg){
          console.log( "Data Saved: " + "保存成功" );
        },
        error: function (msg) {
            console.log("进入error" +msg)
        }
    });
    console.log(lnglats)
}
document.getElementById("regeo").onclick = regeoCode;
document.getElementById("clear").onclick = clear;
document.getElementById("save").onclick = save;
var linesearch;

/*公交线路查询*/
function lineSearch() {
    var busLineName = document.getElementById('BusLineName').value;
    if (!busLineName) return;
    //实例化公交线路查询类，只取回一条路线
    if (!linesearch) {
        linesearch = new AMap.LineSearch({
            pageIndex: 1,
            city: '长沙',
            pageSize: 1,
            extensions: 'all'
        });
    }
    //搜索“536”相关公交线路
    linesearch.search(busLineName, function (status, result) {
        map.clearMap()
        if (status === 'complete' && result.info === 'OK') {
            lineSearch_Callback(result);
        } else {
            alert(result);
        }
    });
}

/*公交路线查询服务返回数据解析概况*/
function lineSearch_Callback(data) {
    var lineArr = data.lineInfo;
    var lineNum = data.lineInfo.length;
    if (lineNum == 0) {
    } else {
        for (var i = 0; i < lineNum; i++) {
            var pathArr = lineArr[i].path;
            var stops = lineArr[i].via_stops;
            var startPot = stops[0].location;
            var endPot = stops[stops.length - 1].location;
            if (i == 0) //作为示例，只绘制一条线路
                drawbusLine(startPot, endPot, pathArr);

        }
    }
}

/*绘制路线*/
function drawbusLine(startPot, endPot, BusArr) {
    //绘制起点，终点
    new AMap.Marker({
        map: map,
        position: startPot, //基点位置
        icon: "https://webapi.amap.com/theme/v1.3/markers/n/start.png",
        zIndex: 10
    });
    new AMap.Marker({
        map: map,
        position: endPot, //基点位置
        icon: "https://webapi.amap.com/theme/v1.3/markers/n/end.png",
        zIndex: 10
    });
    //绘制乘车的路线
    busPolyline = new AMap.Polyline({
        map: map,
        path: BusArr,

        strokeColor: "#09f",//线颜色
        strokeOpacity: 0.8,//线透明度
        isOutline: true,
        outlineColor: 'white',
        strokeWeight: 6//线宽
    });
    map.setFitView();
}

lineSearch();
document.getElementById('search').onclick = lineSearch;
</script>
</body>
</html>
