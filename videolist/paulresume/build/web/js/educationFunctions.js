/* 
 * Functions for Create Resume - Education
 */

//Datepicker for graduation date

$(function () {
    $(".datepicker").datepicker();
});


//Adds an additional form to allow the user to input multiple education entries

$(document).ready(function () {
    $('#btnAddEducation').click(function () {
        $('#additionalEducation').append($('<div>').load('eduForm.html'));
        return false;
    });

    //Loads the Education Form
    $("#eduForm").load("eduForm.html");
});

//Submits all forms on page

function post_form_data(data) {
    $.ajax({
        type: 'POST',
        url: '../process/processEducation',
        data: data,
        async: false
                //success: ,
                //error:
    });
}//end of post_form_data


//When Next button is clicked, all forms on page are submitted for processing
function submitEdu() {
    //$('btnSubmit').on('click', function () {
    $('form').each(function () {
        post_form_data($(this).serialize());
    });
    window.location.href = 'workExperience.jsp';

}
;








