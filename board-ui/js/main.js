// board-ui/js/main.js

document.addEventListener("DOMContentLoaded", function () {
  const postList = document.getElementById("postList");
  const postForm = document.getElementById("postForm");

  // 전체 게시글 조회
  if (postList) {
    fetch("http://localhost:8080/posts")
      .then((res) => res.json())
      .then((data) => {
        postList.innerHTML = data
          .map((p) => `<li><a href="view.html?id=${p.id}">${p.title}</a></li>`)
          .join("");
      });
  }

  // 게시글 작성
  if (postForm) {
    postForm.addEventListener("submit", function (e) {
      e.preventDefault();
      const formData = new FormData(postForm);
      const post = {
        profileId: 1, // 임시 고정
        view: 0,
        file: null,
        title: formData.get("title"),
        price: parseInt(formData.get("price")),
        content: formData.get("content"),
        status: "ONSALE",
      };

      fetch("http://localhost:8080/posts", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(post),
      }).then(() => {
        alert("작성 완료!");
        window.location.href = "index.html";
      });
    });
  }

  // 게시글 상세 보기
  const params = new URLSearchParams(window.location.search);
  const postId = params.get("id");
  if (postId && window.location.pathname.includes("view.html")) {
    fetch(`http://localhost:8080/posts/${postId}`)
      .then((res) => res.json())
      .then((p) => {
        document.getElementById("title").textContent = p.title;
        document.getElementById("content").textContent = p.content;
        document.getElementById("price").textContent = `가격: ${p.price}원`;
        document.getElementById("status").textContent = `상태: ${p.status}`;
      });
  }
});
