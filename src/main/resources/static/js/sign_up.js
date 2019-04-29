function User(username, email, user_password){
    this.username = username;
    this.email = email;
    this.user_password = user_password;
}

$(document).ready(function() {
    $("#submitBtn").click(function() {
        var username = $("#username").val();
        var email = $("#email").val();
        var user_password = $("#password").val();
        var repeated_password = $("#repeatedPassword").val();
        if (user_password != repeated_password) {
            return console.log("password don't match");
        }
        var user = new User(username, email, user_password);
        console.log(JSON.stringify(user));

        $.ajax({
            type: "POST",
            url : "/sign_up",
            headers: {
                'Accept' : 'application/json',
                'Content-Type' : 'application/json'
            },
            data : JSON.stringify(user),
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
