$(function () {
    // Basic Radial Bar Chart -------> RADIAL CHART
    var options_basic = {
        series: [90],
        chart: {
            fontFamily: "inherit",
            height: 300,
            type: "radialBar",
        },
        colors: ["#615dff"],
        plotOptions: {
            radialBar: {
                hollow: {
                    size: "70%",
                },
                dataLabels: {
                    value: {
                        color: "#a1aab2",
                        show: true,
                    },
                },
            },
        },
        labels: ["Completaron"],
    };

    var chart_radial_basic = new ApexCharts(
        document.querySelector("#chart-radial-basic"),
        options_basic
    );
    chart_radial_basic.render();

    // Basic Radial Bar Chart -------> RADIAL CHART
    var options_basic = {
        series: [70],
        chart: {
            fontFamily: "inherit",
            height: 300,
            type: "radialBar",
        },
        colors: ["#615dff"],
        plotOptions: {
            radialBar: {
                hollow: {
                    size: "70%",
                },
                dataLabels: {
                    value: {
                        color: "#a1aab2",
                        show: true,
                    },
                },
            },
        },
        labels: ["Completaron"],
    };

    var chart_radial_basic = new ApexCharts(
        document.querySelector("#chart-radial-basic1"),
        options_basic
    );
    chart_radial_basic.render();

    // Basic Radial Bar Chart -------> RADIAL CHART
    var options_basic = {
        series: [30],
        chart: {
            fontFamily: "inherit",
            height: 300,
            type: "radialBar",
        },
        colors: ["#615dff"],
        plotOptions: {
            radialBar: {
                hollow: {
                    size: "70%",
                },
                dataLabels: {
                    value: {
                        color: "#a1aab2",
                        show: true,
                    },
                },
            },
        },
        labels: ["Completaron"],
    };

    var chart_radial_basic = new ApexCharts(
        document.querySelector("#chart-radial-basic2"),
        options_basic
    );
    chart_radial_basic.render();

    // Basic Radial Bar Chart -------> RADIAL CHART
    var options_basic = {
        series: [10],
        chart: {
            fontFamily: "inherit",
            height: 300,
            type: "radialBar",
        },
        colors: ["#615dff"],
        plotOptions: {
            radialBar: {
                hollow: {
                    size: "70%",
                },
                dataLabels: {
                    value: {
                        color: "#a1aab2",
                        show: true,
                    },
                },
            },
        },
        labels: ["Completaron"],
    };

    var chart_radial_basic = new ApexCharts(
        document.querySelector("#chart-radial-basic3"),
        options_basic
    );
    chart_radial_basic.render();
});