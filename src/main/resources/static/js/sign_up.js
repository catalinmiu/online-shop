function User(username, email, user_password){
    this.username = username;
    this.email = email;
    this.user_password = user_password;
}

function validateEmail(email) {
    var re = /^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
    if (email == '' || !re.test(email))
    {
        return false;
    }
    return true;
}

$(document).ready(function() {
    $("#submitBtn").click(function() {
        var username = $("#username").val();
        var email = $("#email").val();
        var user_password = $("#password").val();
        var repeated_password = $("#repeatedPassword").val();

        if (username == "" || email == "" || user_password == "" || repeated_password == "") {
            $("#errorMsg").text("All fields are required!");
            return ;
        }

        if (user_password != repeated_password) {
            $("#errorMsg").text("Passwords don't match!");
            return ;
        }
        if (!validateEmail(email)) {
            $("#errorMsg").text("Not a valid email address!");
            return ;
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
                window.location.href = "/";
            },
            error: function(request, status, error) {
                $("#errorMsg").text("There is another user with the same username. Change it and try again!");
                var val = request.responseText;
                console.log(val);
            },
            dataType : "text"
        });
    });
});
