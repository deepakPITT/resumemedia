/* 
 * Functions for Create Resume - Work Experience
 */


//Presents a date picker for the Start and End Date fields

$(function () {
    $(".datepicker").datepicker();
});

//Loads the WorkExperience Form

$(document).ready(function () {
    $("#workExpForm").load("workExpForm.html");
});


//When "Add Work Experience" button is pressed, adds an additional form to allow the user to input multiple work entries-->

$(document).ready(function () {
    $('#btnAddWorkExp').click(function () {
        $('#additionalWorkExp').append($('<div>').load('workExpForm.html'));
        return false;
    });
});



//Submits all forms on page

function post_form_data(data) {
    $.ajax({
        type: 'POST',
        url: '../process/processWorkExperience',
        data: data,
        async: false
                //success: ,
                //error:
    });
}//end of post_form_data


//When Next button is clicked, all forms on page are submitted for processing
function submitWork() {
    //$('btnSubmit').on('click', function () {
    $('form').each(function () {
        post_form_data($(this).serialize());
    });
    window.location.href = 'menu.jsp';

}
;
        