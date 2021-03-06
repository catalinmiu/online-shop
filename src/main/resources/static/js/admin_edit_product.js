function Product(title, price, description, stock, categoryId){
    this.title = title;
    this.price = price;
    this.description = description;
    this.stock = stock;
    this.categoryId = categoryId;
    this.id = window.location.pathname.split('/')[2];
}

$(document).ready(function() {
    $("#submitBtn").click(function() {
        var title = $("#productTitle").val();
        var price = $("#productPrice").val();
        var description = $("#productDescription").val();
        var stock = $("#productStock").val();
        var categoryId = $("#categoryId").val();
        var product = new Product(title, price, description, stock, categoryId);

        $.ajax({
            type: "POST",
            url : "/admin_edit_product",
            headers: {
                'Accept' : 'application/json',
                'Content-Type' : 'application/json'
            },
            data : JSON.stringify(product),
            success : function(data) {
                location.reload();
            },
            error: function(request, status, error) {
                var val = request.responseText;
                console.log(val);
            },
            dataType : "text"
        });
    });

});
