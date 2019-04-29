function Category(title){
    this.title = title;
}

$(document).ready(function() {
    $("#submitBtn").click(function() {
        var title = $("#productTitle").val();
        var category = new Category(title);
        console.log(JSON.stringify(category));

        $.ajax({
            type: "POST",
            url : "/create_category",
            headers: {
                'Accept' : 'application/json',
                'Content-Type' : 'application/json'
            },
            data : JSON.stringify(category),
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
