//图表
var chartOption;
// 基于准备好的dom，初始化echarts实例
var chart = echarts.init(document.getElementById('charts'));

/**
 * 默认加载cpu数据
 */
var monitorType = "cpu";

/**
 * 修改此时的加载数据
 */
function monitor(type) {
    monitorType = type;
    chart.clear();
    if (monitorType === "disk") {
        $("#charts").css("display", "none");
    } else {
        $("#charts").css("display", "");
        $("#chartsDisk").empty();
        $("#chartsDisk").css("height", "0");
    }
}

/**
 * 获得cpu数据
 */
function search() {
    var system = $("#systemName").val();
    var timeRange = $("#dateTime").val();
    console.log(system + " " + timeRange + " " + monitorType);
    if (monitorType === "cpu") {
        cpu(system, timeRange);
    } else if (monitorType === "disk") {
        disk(system, timeRange);
    } else if (monitorType === "memory") {
        memory(system, timeRange);
    } else if (monitorType === "gc") {
        gc(system, timeRange);
    } else if (monitorType === "headmemory") {
        headMemory(system, timeRange);
    } else if (monitorType === "thread") {
        thread(system, timeRange);
    }

}


function headMemory(system, timeRange) {
    $.post("/headmemory/getdata", {
        system: system,
        timeRange: timeRange
    }, function (data) {
        console.log(data);
        if (data.code === 500) {
            alert(data.msg);
        } else {
            builderCharts(data, "堆内存数据");
        }
    });
}

function memory(system, timeRange) {
    $.post("/memory/getdata", {
        system: system,
        timeRange: timeRange
    }, function (data) {
        console.log(data);
        if (data.code === 500) {
            alert(data.msg);
        } else {
            builderCharts(data, "内存数据");
        }
    });
}

function thread(system, timeRange) {
    $.post("/thread/getdata", {
        system: system,
        timeRange: timeRange
    }, function (data) {
        console.log(data);
        if (data.code === 500) {
            alert(data.msg);
        } else {
            builderCharts(data, "线程数据");
        }
    });
}

function gc(system, timeRange) {
    $.post("/gc/getdata", {
        system: system,
        timeRange: timeRange
    }, function (data) {
        console.log(data);
        if (data.code === 500) {
            alert(data.msg);
        } else {
            builderCharts(data, "gc数据");
        }
    });
}

/**
 * 查disk
 */
function disk(system, timeRange) {
    $.post("/disk/getdata", {
        system: system,
        timeRange: timeRange
    }, function (data) {
        console.log(data);
        if (data.code === 500) {
            alert(data.msg);
        } else {
            chart.clear();
            var height = 0;
            $.each(data.eCharts, function (index, item) {
                height += 1;
                var charts = builderMoreCharts(item);
                var id = "charts" + index;
                var div = "<div id=" + id + " style=\"height:500px;\"></div>";
                $("#chartsDisk").append(div);
                var chartDiv = echarts.init(document.getElementById(id));
                chartDiv.setOption(charts);
            });
            $("#chartsDisk").css("height",height * 500);
        }
    });
}

/**
 * 查cpu
 */
function cpu(system, timeRange) {

    $.post("/cpu/getdata", {
        system: system,
        timeRange: timeRange
    }, function (data) {
        console.log(data);
        if (data.code === 500) {
            alert(data.msg);
        } else {
            builderCharts(data, "cpu数据");
        }
    });
}

/**
 * 根据后台返回数据构建图表，适合单个图表，不适合磁盘
 */
function builderCharts(data, chartName) {
    var series = [];
    for (var k in data.ydatas) {  //通过定义一个局部变量k遍历获取到了map中所有的key值
        var value = data.ydatas[k];
        var obj = {
            name: k,
            type: 'line',
            stack: k,
            connectNulls: true,
            data: value
        };
        series.push(obj);
    }
    chartOption = {
        title: {
            text: chartName
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: data.series
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        xAxis: {
            axisLabel: {
                interval: 8,
                inside: false,
                rotate: 20,
                formatter: function (value) {
                    console.log("value:" + value);
                    var time = value.split("T");
                    var yearMonthDay = time[0].split("-");
                    var year = Number(yearMonthDay[0]);
                    var month = Number(yearMonthDay[1]);
                    var day = Number(yearMonthDay[2]);
                    var hourMinutesSecond = time[1].split(":");
                    var hour = Number(hourMinutesSecond[0]);
                    var minutes = Number(hourMinutesSecond[1]);
                    var second = Number(hourMinutesSecond[2].substring(0,2));
                    var date = new Date(year, month - 1,day, hour, minutes, second);
                    date.setHours(date.getHours() + 8);
                    var str = Number(date.getMonth()) + 1;
                    return date.getFullYear() + "-" + str + "-" + date.getDate() + " " +
                        date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
                }
            },
            type: 'category',
            boundaryGap: false,
            data: data.xdatas
        },
        yAxis: {
            type: 'value'
        },
        series: series
    };
    chart.setOption(chartOption);
}

/**
 * 根据后台返回数据构建图表，适合多个图表
 */
function builderMoreCharts(data) {
    var series = [];
    for (var k in data.ydatas) {  //通过定义一个局部变量k遍历获取到了map中所有的key值
        var value = data.ydatas[k];
        var obj = {
            name: k,
            type: 'line',
            stack: k,
            connectNulls: true,
            data: value
        };
        series.push(obj);
    }
    chartOption = {
        title: {
            text: data.chartsName
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: data.series
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        xAxis: {
            axisLabel: {
                interval: 8,
                inside: false,
                rotate: 20,
                formatter: function (value) {
                    var time = value.split("T");
                    return time[0] + " " + time[1].split(".")[0];
                }
            },
            type: 'category',
            boundaryGap: false,
            data: data.xdatas
        },
        yAxis: {
            type: 'value'
        },
        series: series
    };
    return chartOption;
}


/**
 * 日期
 */
layui.use(['laydate','element'], function () {
    var laydate = layui.laydate;

    //时间范围选择
    laydate.render({
        elem: '#dateTime'
        , type: 'datetime'
        , theme: 'molv'
        , istime: true
        , range: '~'
    });
});
