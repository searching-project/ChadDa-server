// url로 user_sid 받기
let receivedData = location.href.split('?')[1];
receivedData *= 1;
console.log(receivedData);

// 페이지 로딩되자마자 함수 실행하기
$(document).ready(function () {
    findUserPosts(receivedData);
    alert("조회 완료");
})

// 유저의 포스트를 찾아 얻어오는 함수
function findUserPosts(userSid) {
    $('#search-result-box-post').empty();
    alert("유저의 게시글을 조회중입니다.")

    $.ajax({
        type: 'GET',
        url: `/api/user/${userSid}/posts`,
        contentType: "application/json",
        success: function (response) {
            // 1. view 적용
            for (let i = 0; i < response.data.length; i++) {
                let itemDto = response.data[i];
                let tempHtml = addPostHTML(itemDto);
                $('#search-result-box-post').append(tempHtml);
            }
        }
    })
}

// 포스트 추가 함수
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