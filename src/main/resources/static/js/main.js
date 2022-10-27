$(document).ready(function () {
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


    // id 가 query 인 녀석 위에서 엔터를 누르면 execSearch() 함수를 실행.
    $('#query').on('keypress', function (e) {
        if (e.key == 'Enter') {
            execSearch();
        }
    });

    $('#close').on('click', function () {
        $('#container').removeClass('active');
    })

    $('.nav div.nav-see').on('click', function () {
        $('div.nav-see').addClass('active');
        $('div.nav-search').removeClass('active');

        $('#see-area').show();
        $('#search-area').hide();
    })
    $('.nav div.nav-search').on('click', function () {
        $('div.nav-see').removeClass('active');
        $('div.nav-search').addClass('active');

        $('#see-area').hide();
        $('#search-area').show();
    })

    $('#see-area').hide();
    $('#search-area').show();
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

function execSearch() {
    /**
     * 검색어 input id: query
     * 검색결과 목록: #search-result-box
     * 검색결과 HTML 만드는 함수: addHTML
     */
        // 1. 검색창의 입력값을 가져온다.
    let query = $('#query').val();

    // 2. 검색창 입력값을 검사하고, 입력하지 않았을 경우 focus.
    if (query == '') {
        alert('검색어를 입력해주세요');
        $('#query').focus();
        return;
    }

    // 3. GET /api/search/${query} 요청
    $.ajax({
        type: 'GET',
        url: `/api/search/post/${query}`,
        success: function (response) {
            $('#search-result-box-post').empty();
            if (response.length === 0) {
                $('#search-result-box-post').append("검색 결과가 없습니다.");
            } else {
                for (let i = 0; i < response.length; i++) {
                    let itemDto = response[i];
                    let tempHtml = addPostHTML(itemDto);
                    $('#search-result-box-post').append(tempHtml);
                }
            }
        }
    })

    $.ajax({
        type: 'GET',
        url: `/api/search/user/${query}`,
        success: function (response) {
            $('#search-result-box-profile').empty();
            if (response.data.length === 0) {
                $('#search-result-box-profile').append("검색 결과가 없습니다.");
            } else {
                for (let i = 0; i < response.data.length; i++) {
                    let itemDto = response.data[i];
                    let tempHtml = addProfileHTML(itemDto);
                    $('#search-result-box-profile').append(tempHtml);
                }
            }
        }
    })

    $.ajax({
        type: 'GET',
        url: `/api/search/location/${query}`,
        success: function (response) {
            $('#search-result-box-location').empty();
            if (response.data.length === 0) {
                $('#search-result-box-location').append("검색 결과가 없습니다.");
            } else {
                console.log(response)
                for (let i = 0; i < response['data'].length; i++) {
                    let itemDto = response['data'][i];
                    console.log(itemDto)
                    let tempHtml = addLocationHTML(itemDto);
                    $('#search-result-box-location').append(tempHtml);
                }
            }
        }
    })
}

function addProfileHTML(itemDto) {
    let isbusiness = itemDto.businessAccountTf === false ? "" : "✔"
    return `<div class="search-itemDto" >
                <div class="search-itemDto-center">
                    <div class="name" id="${itemDto.profileName}" onclick="moveToUserPosts(${itemDto.sid})" style="cursor:pointer">
                         ${itemDto.profileName}
                        <span class="unit business">${isbusiness}</span>
                        <span class="unit business">@${itemDto.firstnameLastname}</span>
                    </div>
                    <div>
                        <span class="unit">게시글 </span>
                        <span class="unit post"> ${itemDto.nposts}</span>
                        <span class="unit link" onclick = "moveToUserPosts(${itemDto.sid})" style="cursor:pointer"> 🔗링크</span>
                        <span class="unit">/ 팔로잉</span>
                        <span class="unit following"> ${itemDto.following}</span>
                        <span class="unit">명 /</span>
                        <span class="unit">팔로워 </span>
                        <span class="unit followers"> ${itemDto.followers}</span>
                        <span class="unit">명</span>
                    </div>
                    <div> ${itemDto.description} </div>
                    <div> ${itemDto.url}</div>
                </div>
            </div>`
}

function findProfile(profileId) {
    $.ajax({
        type: "GET",
        url: `/api/post/${profileId}/user`,
        contentType: "application/json",
        success: function (response) {
            response = response['data']
            let isbusiness = response.businessAccountTf === true ? "✔" : ""
            $('#profile-detail').empty();
            let html = `<h1 class="name" id="profile-detail-name">
                ${response.profileName}
                <span class="unit business" id="profile-detail-business">${isbusiness}</span>
            </h1>
                <span class="unit">게시물</span>
                <span class="unit like" id="profile-detail-post"> ${response.nPosts}</span>
                <span class="unit">/ 팔로잉</span>
                <span class="unit like" id="profile-detail-following"> ${response.following}</span>
                <span class="unit">명 /</span>
                <span class="unit" >팔로워 </span>
                <span class="unit comment" id="profile-detail-follower"> ${response.followers}</span>
                <span class="unit">명</span>
            <div id="profile-detail-description"> ${response.description} </div>
            <div id="profile-detail-url"> ${response.url}</div>
        </div>`
            $('#profile-detail').append(html);
            // 2. 응답 함수에서 modal을 뜨게 함
            $('#container').addClass('active');

        }
    })
}

function addPostHTML(itemDto) {
    let location_name = itemDto.name === null ? "" : "@" + itemDto.name
    let like_num = itemDto.number_likes === null ? 0 : itemDto.number_likes
    let comment_num = itemDto.number_comments === null ? 0 : itemDto.number_comments
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

function addLocationPostHTML(postDto) {
    let location_name = itemDto.name === null ? "" : "@" + itemDto.name
    let like_num = itemDto.numbr_likes === null ? 0 : itemDto.numbr_likes
    let comment_num = itemDto.number_comments === null ? 0 : itemDto.number_comments
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

function moveToUserPosts(userSid) {
    window.location.href = "user-posts?" + userSid
    $('#search-result-box-post').empty();
    findUserPosts(userSid);
}

function addLocationHTML(itemDto) {
    return `<div class="search-itemDto" id="${itemDto.sid}" onclick="movetoLocationPost(${itemDto.sid})">
            <div class="search-itemDto-center">
                <div class="name">
                     ${itemDto.name}
                </div>
                <div>
                    <span class="unit region"> ${itemDto.region} </span>
                    <span class="unit city"> ${itemDto.city} </span>
                    <span class="unit street"> ${itemDto.street}</span>
                </div>
            </div>
        </div>`
}

function movetoLocationPost(LocationId) {
    console.log("locationPost?" + LocationId)
    window.location.href = "locationPost?" + LocationId
}



