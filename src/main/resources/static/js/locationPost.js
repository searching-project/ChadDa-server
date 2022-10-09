$(document).ready(function () {
    let query = location.href.split('?')[1];
    findLocationPost(query);
})

function findLocationPost(id){
    console.log(id);
    $.ajax({
        type: "GET",
        url: `/api/search/location/${id}/post`,
        contentType: "application/json",
        success: function (response) {
            $('#search-result-box-post').empty();
            for (let i = 0; i < response.data.length; i++){
                let itemDto = response.data[i];
                let tempHtml = addPostHTML(itemDto);
                $('#search-result-box-post').append(tempHtml)
            }
        }
    })
}

function addPostHTML(itemDto) {
    let location_name = itemDto.name===null? "": "@"+itemDto.name
    let like_num = itemDto.numbr_likes===null? 0: itemDto.numbrLikes
    let comment_num = itemDto.number_comments===null? 0: itemDto.numberComments
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