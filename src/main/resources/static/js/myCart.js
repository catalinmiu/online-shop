function Product(id, units) {
    this.product_id = id;
    this.units = units;
}

function calculateTotalPrice() {
    var prices = document.getElementsByClassName("price");
    var units = document.getElementsByClassName("units");
    var totalPrice = 0;
    for(var i=0;i<prices.length;i++) {
        totalPrice += (prices[i].id * units[i].value);
    }
    return totalPrice;
}

function showTotalPrice() {
    var totalPriceValue = calculateTotalPrice();
    var totalPriceElement = $('#totalPrice');
    totalPriceElement.text(totalPriceValue.toFixed(2));
}

function getAllProducts() {
    var productElements = document.getElementsByClassName("product");
    var products = [];
    for (var i = 0; i < productElements.length; i ++) {
        var id = productElements[i].id;
        var units = productElements[i].children[1].value;
        var product = new Product(id, units);
        products.push(product);
    }
    return products;
}
$(document).ready(function() {
    $('body').on('change', '.units', function(){{
        showTotalPrice();
    }})

    $("#checkoutBtn").click(function(event) {
        var products = getAllProducts();

        $.ajax({
            type: "POST",
            url : "/myCart-checkout",
            headers: {
                'Accept' : 'application/json',
                'Content-Type' : 'application/json'
            },
            data : JSON.stringify(products),
            success : function(data) {
                window.location.href = "/";
            },
            error: function(request, status, error) {
                $("#error").text("There was a problem. Please refresh this page and try again");
                var val = request.responseText;
                console.log(val);
            },
            dataType : "text"
        });
    });
    $("#saveBtn").click(function(event) {
            var products = getAllProducts();

            $.ajax({
                type: "POST",
                url : "/myCart-save",
                headers: {
                    'Accept' : 'application/json',
                    'Content-Type' : 'application/json'
                },
                data : JSON.stringify(products),
                success : function(data) {
                    window.location.href = "/myCart";
                },
                error: function(request, status, error) {
                    $("#error").text("There was a problem. Please refresh this page and try again");
                    var val = request.responseText;
                    console.log(val);
                },
                dataType : "text"
            });
        });
});
