var subscription = null

function registerTemplate() {
    template = $("#template").html();
    Mustache.parse(template);
}

function connect() {
    var sock = new SockJS('/twitter');
    client = Stomp.over(sock);
    client.connect({}, function(state) {
        console.log('State ' + state);
    });
}

function addTweet(tweet) {
    $("#resultsBlock").prepend(Mustache.render(template, JSON.parse(tweet.body)));
    console.log("Tweet added")
}

function registerSearch() {
    $("#search").submit(function(event){
        event.preventDefault();
        var target = $(this).attr('action');
        var query = $("#q").val();

        if (subscription != null) {
            $("#resultsBlock").empty();
            subscription.unsubscribe();
            console.log("Unsubscription")

        }

        client.send("/app/" + target, {}, query);
        subscription = client.subscribe('/queue/search/' + query, addTweet);
        console.log("Client subscribed");

    });
}

$(document).ready(function() {
    registerTemplate();
    connect();
	registerSearch();
});


