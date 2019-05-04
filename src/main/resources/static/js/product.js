

$(document).ready(function() {
    $("#submitBtn").click(function() {
        var url = window.location.pathname;

        $.ajax({
            type: "POST",
            url : url,
            headers: {
                'Accept' : 'application/json',
                'Content-Type' : 'application/json'
            },
//            data : JSON.stringify(product),
            success : function(data) {
                console.log("succes");
            },
            error: function(request, status, error) {
                var val = request.responseText;
                console.log(val);
            },
            dataType : "text"
        });
    });

});
