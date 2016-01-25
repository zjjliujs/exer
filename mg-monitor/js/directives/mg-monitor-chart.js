app.directive("mgMonitorChart",
["$timeout",
function($timeout){

    function randomData() {
        return Math.random() * 9;
    }

    function link(scope,elem,attrs) {
        //console.log (elem.width());
        //console.log (elem.height());
        //console.log (attrs.id);

        var numberOfSeries = 2,
        numberOfDataPoint = 11,
        data = [];

        for (var i = 0; i < numberOfSeries; ++i) {
            data.push(d3.range(numberOfDataPoint).map(function (i) {
                return {x: i, y: randomData()};
            }));
        }

        var chart = lineChart()
        .container(attrs.id)
        .width (elem.width())
        .height(elem.height())
        .x(d3.scale.linear().domain([0, 10]))
        .y(d3.scale.linear().domain([0, 10]));

        data.forEach(function (series) {
            chart.addSeries(series);
        });

        chart.render();
    }

    return {
        link:link
    }
}])
