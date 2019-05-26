$(document).ready(function() {
    $("#editBtn").click(function() {
        var productId = $("#productId").val();
        window.location.href = "/admin_edit_product/" + productId;
    });
    $("#deleteBtn").click(function() {
        var productId = $("#productId").val();

        $.ajax({
            type: "POST",
            url : "/admin_products_delete",
            headers: {
                'Accept' : 'application/json',
                'Content-Type' : 'application/json'
            },
            data : JSON.stringify(productId),
            success : function(data) {
                    window.location.href = "/admin_products";
            },
            error: function(request, status, error) {
                var val = request.responseText;
                console.log(val);
            },
            dataType : "text"
        });
    });

});
