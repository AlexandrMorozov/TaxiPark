function loadStatistics()
{
    var xData=document.getElementById("xAxis");
    var yData=document.getElementById("yAxis");

    var x=xData.split(",");
    var y=yData.split(",");

    alert("dfd");

    $.getJSON('https://www.highcharts.com/samples/data/jsonp.php?filename=usdeur.json&callback=?', function (data) {
        $toolsPeriod = $('.js-valuta-tools-period'),
            $toolsView = $('.js-valuta-tools-view'),

            Highcharts.chart('container', {
                chart: {
                    zoomType: 'x'
                },
                title: {
                    text: ''
                },
                xAxis: {
                    categories: ['фев.12','мар.12','апр.12','май.12','июн.12','июл.12','авг.12','сен.12','окт.12','ноя.12','дек.12','янв.13','фев.13','мар.13','апр.13','май.13','июн.13','июл.13','авг.13','сен.13','окт.13','ноя.13','янв.14','мар.14','апр.14','май.14','июл.14','авг.14','окт.14','ноя.14','дек.14','янв.15','фев.15','мар.15','апр.15','май.15','июн.15','июл.15','авг.15','сен.15','окт.15','ноя.15','янв.16','фев.16','мар.16','апр.16','май.16','июн.16','июл.16','авг.16','сен.16','ноя.16','дек.16','янв.17','фев.17','мар.17','апр.17'],
                    tickmarkPlacement: 'on',
                    title: {
                        enabled: false
                    },
                    gridLineColor: '#dadada',
                    gridLineWidth: 0,
                },
                yAxis: {
                    opposite: false,
                    endOnTick: false,
                    gridLineColor: '#dadada',
                    gridLineWidth: 1,
                    maxPadding: 0.02,
                    tickLength: 0,
                    labels: {
                        style: {
                            color: '#888'
                        }
                    },
                    showFirstLabel: false,
                    title: {
                        text: null
                    }
                },
                legend: {
                    enabled: false
                },
                tooltip: {
                    crosshairs: true,
                    shared: true,
                    valueSuffix: ' %',
                    zIndex: 10
                },

                plotOptions: {
                    area: {
                        fillColor: {
                            linearGradient: {
                                x1: 0,
                                y1: 0,
                                x2: 0,
                                y2: 1
                            },
                            stops: [
                                [0, "#80889E"],
                                [1, "#bdc2cd"]
                            ]
                        },
                        lineColor: '#ffd249',
                        marker: {
                            radius: 2,
                            lineColor:"#00113c",
                            fillColor: "#fff"

                        },
                        lineWidth: 1,
                        states: {
                            hover: {
                                lineWidth: 1
                            }
                        },
                        threshold: null
                    }
                },

                series: [{
                    type: 'area',
                    name: "Проценты",
                    data: [
                        22.246,39.592,39.984,30.184,19.453,27.293,42.483,57.869,70.119,68.649,60.025,60.025,61.201,76.195,79.037,83.545,98.441,101.283,98.343,94.325,103.635,106.477,117.355,120.981,131.467,140.287,143.129,141.071,134.799,156.359,152.047,178.409,174.587,174.195,179.487,179.095,184.191,191.639,208.593,205.653,227.507,229.271,236.915,242.599,255.143,255.829,257.005,267.099,273.273,275.429,273.665,272.391,280.427,286.699,287.091,289.247,296.009,
                    ]
                }
                ]
            });
    });




}