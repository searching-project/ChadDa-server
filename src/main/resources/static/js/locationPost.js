$(document).ready(function () {
    let query = location.href.split('?')[1];
    findLocationPost(query);

    if ($.cookie('access') && $.cookie('refresh')) {
        showLogin(true)
        console.log("success authorization")
        $.ajaxSetup({
            headers: {
                'Authorization': $.cookie('access'),
                'Refresh-Token': $.cookie('refresh')
            }
        })
        showUserInfo()

    } else if ($.cookie('refresh')) {
        reissue()
    } else {
        showLogin()
        console.log("No access")
    }
});

function showUserInfo() {
    $.ajax({
        type: "POST",
        url: `/user/userinfo`,
        contentType: "application/json",
        headers: {
            'Authorization': $.cookie('access'),
            'Refresh-Token': $.cookie('refresh')
        },
        success: function (response) {
            const username = response.username;
            if (!username) {
                console.log("username not found")
            } else {
                $('#username').text(username);
            }
        },
        error: function () {
            console.log("find userinfo failed")
        }
    })
}

function showLogin(isAuth) {
    if (isAuth) {
        $('#signout_form').show();
        $('#signin_form').hide();
    } else {
        $('#signout_form').hide();
        $('#signin_form').show();
    }
}

function reissue() {
    $.ajaxSetup({
        headers: {
            'Refresh-Token': $.cookie('refresh')
        }
    })
    $.ajax({
        type: "POST",
        url: `/user/reissue`,
        contentType: "application/json",
        success: function (response, status, request) {
            const accessToken = request.getResponseHeader('Authorization')
            const refreshToken = request.getResponseHeader('Refresh-Token')
            if (accessToken && refreshToken) {
                $.ajaxSetup({
                    headers: {
                        'Authorization': $.cookie('access', accessToken, {
                            path: '/',
                            expires: new Date(Date.now() + 30 * 60 * 1000)
                        }),
                        'Refresh-Token': $.cookie('refresh', refreshToken, {
                            path: '/',
                            expires: new Date(Date.now() + 24 * 60 * 60 * 1000)
                        })
                    }
                });
                console.log(request.getResponseHeader('Authorization'))
                console.log(request.getResponseHeader('Refresh-Token'))
                window.location.reload();
            } else {
                console.log("reissue failed")
                showLogin()
            }
        }, error: function (request, status, error) {
            console.log(request)
            console.log(status)
            console.log(error)
            console.log("reissue error")
            showLogin()
        }
    })
}

function findLocationPost(id) {
    console.log(id);
    $.ajax({
        type: "GET",
        url: `/api/search/location/${id}/post`,
        contentType: "application/json",
        beforeSend: function () {
            FunLoadingBarStart();      	//로딩바 생성
        },
        success: function (response) {
            $('#search-result-box-post').empty();
            if (response.data.length === 0) {
                alert("검색결과가 없습니다.")
                window.location.href = "/"
            }
            for (let i = 0; i < response.data.length; i++) {
                let itemDto = response.data[i];
                let tempHtml = addPostHTML(itemDto);
                $('#search-result-box-post').append(tempHtml)
            }
        }
    })
}

function addPostHTML(itemDto) {
    let location_name = itemDto.name === null ? "" : "@" + itemDto.name
    let like_num = itemDto.numbr_likes === null ? 0 : itemDto.numbrLikes
    let comment_num = itemDto.number_comments === null ? 0 : itemDto.numberComments
    return `<div class="search-itemDto" id="${itemDto.sid}" onclick="findProfile(${itemDto.sid_profile})" >
            <div class="search-itemDto-center" >
                <div class="name" >
                    ${itemDto.profile_name}
                    <span class="unit"> ${location_name}</span>
                </div>
                <div>
                    <span class="unit">좋아요</span>
                    <span class="unit like">${like_num}</span>
                    <span class="unit">개 /</span>
                    <span class="unit">댓글 </span>
                    <span class="unit comment">${comment_num}</span>
                    <span class="unit">개</span>
                </div>
                <div>${itemDto.description}</div>
            </div>
        </div>`
}


function FunLoadingBarStart() {
    $('#search-result-box-post').empty();
    var backGroundCover = "<div id='back'></div>";
    var loadingBarImage = '';
    loadingBarImage += "<div id='loadingBar'>";
    loadingBarImage += "     <img src='images/loading.gif' style='height: 50px'/>";
    loadingBarImage += "</div>";
    $('#search-result-box-post').append(backGroundCover).append(loadingBarImage);
}
