$(document).ready(function () {

    findUserPosts(userSid);

    $('#search-area').show();
})

function addPostHTML(itemDto) {
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

function findUserPosts(userSid) {
    // window.location.href = "user-posts"
    // window.location.href = "user-posts.html"
    // window.location.href = "../../templates/user-posts.html"
    // window.location.href = "@{/user-posts}"
    window.location.href = 'user-posts'
    // window.location.href = "@{/../../user-posts.html}"
    $.ajax({
        type: 'GET',
        url: `/api/user/${userSid}/posts`,
        contentType: "application/json",
        success: function (response) {
            $('#user-posts').empty();

            // 1. view 적용
            for (let i = 0; i < response.data.length; i++) {
                let itemDto = response.data[i];
                let tempHtml = addPostHTML(itemDto);
                $('#user-posts').append(tempHtml);

                // 2. 응답 함수에서 modal을 뜨게 함
                $('#see-area').show();
                $('#search-area').hide();
            }
        }
    })
}