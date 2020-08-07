<%--
  Created by IntelliJ IDEA.
  User: chen
  Date: 2020/8/6
  Time: 17:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
    <title>公交线路查询</title>
    <link rel="stylesheet" href="https://a.amap.com/jsapi_demos/static/demo-center/css/demo-center.css"/>
    <style type="text/css">
        html, body, #container {
            height: 100%;
        }
    </style>
</head>
<body>
<div id="container"></div>
<div class="input-card" style='width:18rem;'>
    <label style='color:grey'>公交线路查询</label>
    <div class="input-item">
        <div class="input-item-prepend"><span class="input-item-text">线路名称</span></div>
        <input id='BusLineName' type="text" value='919'>
    </div>
    <input id="search" type="button" class="btn" value="查询"/>
</div>

<script type="text/javascript"
        src="https://webapi.amap.com/maps?v=1.4.15&key=b4e288825a07a480ca57cd9113411feb&plugin=AMap.LineSearch"></script>
<script language="javascript">
/*
 * 该示例主要流程分为三个步骤
 * 1. 首先调用公交路线查询服务(lineSearch)
 * 2. 根据返回结果解析，输出解析结果(lineSearch_Callback)
 * 3. 在地图上绘制公交线路()
 */
var map = new AMap.Map("container", {
    resizeEnable: true,
    center: [116.397428, 39.90923],//地图中心点
    zoom: 13 //地图显示的缩放级别
});
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
