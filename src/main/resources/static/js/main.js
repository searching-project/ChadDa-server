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
