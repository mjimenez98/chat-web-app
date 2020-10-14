// All custom JS used for Chat Web App

// Makes chat window's scroll go to bottom element
function scrollToBottom() {
    let element = document.getElementById("chat-window");
    element.scrollTop = element.scrollHeight;
}

function switchStylesheet(from) {
    let currentStylesheet = document.getElementById("stylesheet-version").getAttribute("href");
    let stylesheetPath = currentStylesheet.substring(0, currentStylesheet.lastIndexOf('/') + 1);
    let stylesheetCookie = getCookie("stylesheet");

    let currentFavicon = document.getElementById("favicon").getAttribute("href");
    let faviconPath = currentFavicon.substring(0, currentFavicon.lastIndexOf('/') + 1);

    let filename, favicon;

    // If call is coming from body onload
    //   - If undefined set a stylesheet A
    //   - Else set the one coming from the cookie
    if (stylesheetCookie === "") {
        filename = "version-a";
        favicon = "favicon1";
    } else {
        if (from === "body") {
            filename = stylesheetCookie;

            // Toggle to reflect stylesheet B
            if (filename === "version-b") {
                $('#stylesheet-switcher').bootstrapToggle('toggle')
                favicon = "favicon2";
            }

        } else {
            if (stylesheetCookie === "version-b") {
                filename = "version-a";
                favicon = "favicon1";
            } else {
                filename = "version-b";
                favicon = "favicon2";
            }
        }
    }

    document.getElementById("favicon").setAttribute("href",
        faviconPath + favicon + ".png");
    document.getElementById("stylesheet-version").setAttribute("href",
        stylesheetPath + filename + ".css");
    document.cookie = "stylesheet=" + filename;
}

// Return cookie value of name passed
// Source: https://www.w3schools.com/js/js_cookies.asp
function getCookie(cookieName) {
    let name = cookieName + "=";
    let decodedCookie = decodeURIComponent(document.cookie);
    let cookies = decodedCookie.split(';');

    for (let i=0; i < cookies.length; i++) {
        let cookie = cookies[i];

        while (cookie.charAt(0) === ' ') {
            cookie = cookie.substring(1);
        }
        if (cookie.indexOf(name) === 0) {
            return cookie.substring(name.length, cookie.length);
        }
    }

    return "";
}
