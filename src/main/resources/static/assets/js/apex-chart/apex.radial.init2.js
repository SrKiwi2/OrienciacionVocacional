$(function () {
    let numTestChaside = document.getElementById("num_test_chaside").value;
    let numTestSociales = document.getElementById("num_test_sociales").value;
    let numTestProfesionales = document.getElementById("num_test_profesionales").value;
    let numTestMultiples = document.getElementById("num_test_multiples").value;

    let numEstudiantesTotales = document.getElementById("num_estudiantes_totales").value;

    
    // Basic Radial Bar Chart -------> RADIAL CHART
    var options_basic = {
        series: [(numTestChaside*100)/numEstudiantesTotales],
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
        series: [(numTestSociales*100)/numEstudiantesTotales],
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
        series: [(numTestProfesionales*100)/numEstudiantesTotales],
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
        series: [(numTestMultiples*100)/numEstudiantesTotales],
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