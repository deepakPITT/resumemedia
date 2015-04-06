
var $restPath = "../rest/userws?lastName=";
var $searchField = "lastName";
$dataTitle = "Last Name";
var xhr;
var $option;
$(document).ready(function () {
    getJsonFromWebService($restPath);

    $("#searchCol").text($dataTitle);

    $('#searchOption').on('change', function () {
        $(".data").remove();
        $option = $("#searchOption :selected").text();

        if ($option === "Last Name") {
            $restPath = "../rest/userws?lastName=";
            $searchField = "lastName";
            $dataTitle = "Last Name";

        }
        else if ($option === "Login") {
            $restPath = "../rest/userws?login=";
            $searchField = "login";
            $dataTitle = "Login";
        }
        else if ($option === "Email") {
            $restPath = "../rest/userws?email=";
            $searchField = "email";
            $dataTitle = "Email";
        }

        $("#searchCol").text($dataTitle);
        getJsonFromWebService($restPath + $('#txtSearchbox').val());
    });

    $('#txtSearchbox').keyup(function () {
        if ($("#txtSearchbox").val().length > 0) {
            $(".data").remove();
            abortAjax(xhr);
            $searchTxt = $('#txtSearchbox').val();
            $tempFullPath = $restPath + $searchTxt;
            getJsonFromWebService($tempFullPath);
            $tempFullPath = "";
        }
    });
});
function getJsonFromWebService(targetUrl) {
    xhr = jQuery.ajax({
        type: "GET",
        url: targetUrl,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data, status, jqXHR) {
            addToAutoComplete(data);
            jsonToHtmlTable(data);
        },
        error: function (jqXHR, status) {
            console.log(status);
        }
    });
}

function addToAutoComplete(data) {
    $lookup = $('#divUserLookup');
    $lookup.empty();
    $temp = "";
    for (var i = 0; i < data.length; i++) {
        $temp += '<a href=javascript:addValueToTextBox("' + data[i].lastName + '");>' + data[i].lastName + '</a><br/>';
    }
    $lookup.append($temp);
}

function addValueToTextBox(data) {
    $textBox = $('#txtUserList');
    $textBox.empty();
    $textBox.val(data);
}

function jsonToHtmlTable(data) {

    $tableData = $('#tblUserList');
    $('.data').empty();
    for (var i = 0; i < data.length; i++) {
        var roles = "";
        if (data[i].Employer === "true") {
            roles += " Employer";
        }
        if (data[i].Staff === "true") {
            roles += " Staff";
        }
        if (data[i].Student === "true") {
            roles += " Student";

        }

        var status;
        if (data[i].enabled === false) {
            status = "Disabled";
        }
        else {
            status = "Enabled";
        }

        var $row = $('<tr class ="data">');
        $row.append('<td>' + data[i].lastName + '</td>');
        $row.append('<td>' + data[i].firstName + '</td>');
        $row.append('<td>' + data[i].login + '</td>');
        $row.append('<td>' + data[i].email + '</td>');
        $row.append('<td>' + roles + '</td>');
        $row.append('<td>' + status + '</td>');
        $row.append('<td>' + data[i][$searchField] + '</td>');
        $row.append('<td><a href="viewEditUser.jsp?userID=' + data[i].userID + '">View/Edit</a></td>');
        $row.append('</tr>');
        $tableData.append($row);
    }
}

function abortAjax(xhr) {
    if (xhr && xhr.readystate !== 4) {
        xhr.abort();
    }
}

