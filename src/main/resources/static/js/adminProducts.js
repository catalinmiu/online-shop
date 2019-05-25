$(document).ready(function() {
    $("#deleteBtn").click(function() {
        var productId = $("#productId").val();

        $.ajax({
            type: "POST",
            url : "/admin_products-delete",
            headers: {
                'Accept' : 'application/json',
                'Content-Type' : 'application/json'
            },
            data : JSON.stringify(productId),
            success : function(data) {
                    window.location.href = "/admin-products";
            },
            error: function(request, status, error) {
                var val = request.responseText;
                console.log(val);
            },
            dataType : "text"
        });
    });

});
