$(function () {
    let numTestChaside = document.getElementById("num_test_chaside").value;
    let numTestSociales = document.getElementById("num_test_sociales").value;
    let numTestProfesionales = document.getElementById("num_test_profesionales").value;
    let numTestMultiples = document.getElementById("num_test_multiples").value;

    let numEstudiantesTotales = document.getElementById("num_estudiantes_totales").value;
    console.log(numEstudiantesTotales);
    
    // Basic Radial Bar Chart -------> RADIAL CHART
    var options_basic = {
        series: [numTestChaside],
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
                        formatter: function (val) {
                            return Math.round(val); // Redondea al entero más cercano
                        }
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
        series: [numTestSociales],
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
                        formatter: function (val) {
                            return Math.round(val); // Redondea al entero más cercano
                        }
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
        series: [numTestProfesionales],
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
                        formatter: function (val) {
                            return Math.round(val); // Redondea al entero más cercano
                        }
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
        series: [numTestMultiples],
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
                        formatter: function (val) {
                            return Math.round(val); // Redondea al entero más cercano
                        }
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