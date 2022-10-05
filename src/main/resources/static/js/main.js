$(document).ready(function () {
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
})

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
        url: `/api/posts/search/${query}`,
        success: function (response) {
            $('#search-result-box-post').empty();
            for (let i = 0; i < response.length; i++) {
                let itemDto = response[i];
                let tempHtml = addPostHTML(itemDto);
                $('#search-result-box-post').append(tempHtml);
            }
        }
    })
    $.ajax({
        type: 'GET',
        url: `/api/search/user/${query}`,
        success: function (response) {
            $('#search-result-box-profile').empty();
            // for (let i = 0; i < response.data.length; i++) {
            for (let i = 0; i < 5; i++) {
                let itemDto = response.data[i];
                let tempHtml = addProfileHTML(itemDto);
                $('#search-result-box-profile').append(tempHtml);
            }
        }
    })
    $.ajax({
        type: 'GET',
        url: `/api/locations/search/${query}`,
        success: function (response) {
            $('#search-result-box-location').empty();
            for (let i = 0; i < response.length; i++) {
                let itemDto = response[i];
                let tempHtml = addLocationHTML(itemDto);
                $('#search-result-box-location').append(tempHtml);
            }
        }
    })

}

function addProfileHTML(itemDto) {
    let isbusiness = itemDto.isBusinessAccount === "false"? "" : "✔"
    return `<div class="search-itemDto" id="${itemDto.sid}">
            <div class="search-itemDto-center">
                <div class="name" onclick="findUserPosts(${itemDto.profileName})">
                     ${itemDto.profileName}
                    <span class="unit business">${isbusiness}</span>
                    <span class="unit business">@${itemDto.firstnameLastname}</span>
                </div>
                <div>
                    <span class="unit">게시물</span>
                    <span class="unit post"> ${itemDto.nPosts}</span>
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

function addPostHTML(itemDto) {
    return `<div class="search-itemDto" id="${itemDto.sid}">
            <div class="search-itemDto-center">
                <div class="name">
                    ${itemDto.profileId}
                </div>
                <div>
                    <span class="unit">좋아요</span>
                    <span class="unit like">${itemDto.numbrLikes}</span>
                    <span class="unit">개 /</span>
                    <span class="unit">댓글 </span>
                    <span class="unit comment">${itemDto.numberComments}</span>
                    <span class="unit">개</span>
                </div>
                <div>${itemDto.description}</div>
            </div>
        </div>`
}

function findUserPosts(profileName) {
    window.location.href = "user-posts.html"
    $.ajax({
        type: 'GET',
        url: `/api/user/${profileName}/posts`,
        success: function (response) {
            $('#search-result-box-post').empty();
            response = response.data
            for (let i = 0; i < response.length; i++) {
                let itemDto = response[i];
                let tempHtml = addPostHTML(itemDto);
                $('#search-result-box-post').append(tempHtml);
            }
        }
    })
}

function addLocationHTML(itemDto) {
    return `<div class="search-itemDto" id="${itemDto.sid}>
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

