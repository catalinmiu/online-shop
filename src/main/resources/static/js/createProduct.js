function Product(title, price, description, stock, categoryId){
    this.title = title;
    this.price = price;
    this.description = description;
    this.stock = stock;
    this.categoryId = categoryId;
}

$(document).ready(function() {
    $("#submitBtn").click(function() {
        var title = $("#productTitle").val();
        var price = $("#productPrice").val();
        var description = $("#productDescription").val();
        var stock = $("#productStock").val();
        var categoryId = $("#categoryId").val();
        var product = new Product(title, price, description, stock, categoryId);
        console.log(JSON.stringify(product));

        $.ajax({
            type: "POST",
            url : "/createProduct",
            headers: {
                'Accept' : 'application/json',
                'Content-Type' : 'application/json'
            },
            data : JSON.stringify(product),
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
