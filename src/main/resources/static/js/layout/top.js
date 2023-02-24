/**
 * top main menu 중 조건 검색의 sub menu 시간 검색
 * @param untilTime 의 단위는 hour
 */
function searchMarkByTime(untilTime) {
    // $.ajax({
    //     type: "post",
    //     url: "/search-marks",
    //     async: false,
    //     contentType: 'application/json',
    //     dataType: "json",
    //     data: JSON.stringify(untilTime),
    //     success: function (data, status) {
    //         console.log(JSON.stringify(data));
    //         insertMark(data);
    //     },
    //     error: function (status) {
    //         alert(status);
    //     }
    // });
    init(untilTime);
}


$(function() { // DOM ready
    // If a link has a dropdown, add sub menu toggle.
    $('nav ul li a:not(:only-child)').click(function(e) {
        $(this).siblings('.nav-dropdown').toggle();
        // Close one dropdown when selecting another
        $('.nav-dropdown').not($(this).siblings()).hide();
        e.stopPropagation();
    });
    // Clicking away from dropdown will remove the dropdown class
    $('html').click(function() {
        $('.nav-dropdown').hide();
    });
    // Toggle open and close nav styles on click
    $('#nav-toggle').click(function() {
        $('nav ul').slideToggle();
    });
    // Hamburger to X toggle
    $('#nav-toggle').on('click', function() {
        this.classList.toggle('active');
    });
}); // end DOM ready


// Copyright (c) 2023 by Tania Rascia (https://codepen.io/taniarascia/pen/dYvvYv)
