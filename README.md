
📚 UsedLion-Team4

UsedLion-Team4 | 온라인 중고 거래 플랫폼 | 멋사 백엔드 자바 15기 회고팀4 협업 공간

⸻

🚀 팀원들을 위한 Git 사용 가이드

문서 작성자: 박정환

UsedLion-Team4 프로젝트 협업을 위해 Git 및 GitHub를 효과적으로 사용하는 방법을 안내합니다.
브랜치를 활용하여 안정적인 코드 관리와 원활한 협업이 가능하도록 구성했습니다.

⸻

📌 1. GitHub 저장소 클론하기 (최초 1회)

프로젝트 시작 전 GitHub 저장소를 내 컴퓨터(로컬)로 복제해야 합니다.

맥: 터미널 사용
윈도우: Git Bash 또는 GitHub CLI 사용 추천
(GUI 툴 사용도 가능합니다. 과정은 비슷합니다.)

진행 방법:
	1.	로컬 프로젝트 폴더(ex. ~/Dev/)로 이동합니다.
	2.	브라우저에서 저장소로 이동합니다:
https://github.com/juanpark/UsedLion-Team4.git
	3.	“Code” 버튼 클릭 → “Clone” 옵션에서 본인 환경에 맞는 방식 복사

🔹 HTTPS:

git clone https://github.com/juanpark/UsedLion-Team4.git

🔹 SSH:

git clone git@github.com:juanpark/UsedLion-Team4.git

✅ 클론 완료!
✅ GitHub 인증 방식(HTTPS/SSH/CLI)에 따라 선택하세요.

⸻

📌 2. main에서 직접 작업하지 마세요!

❌ 금지된 방법:

git add .
git commit -m "수정함"
git push origin main   # 🚨 금지! 🚨

👉 main 브랜치는 항상 깨끗하고 안정적인 상태로 유지해야 합니다!

⸻

📌 3. 새로운 브랜치에서 작업하기

(1) 작업 시작 시 브랜치 생성:

git switch -c feature/작업이름

(2) 브랜치 관련 명령어:

git branch          # 모든 브랜치 목록 보기
git switch 브랜치명  # 원하는 브랜치로 이동
git switch -        # 이전 브랜치로 빠르게 이동

📌 예시 브랜치:

feature/add-login
feature/improve-ui
bugfix/fix-login-error

✅ 추천 브랜치 규칙 (UsedLion-Team4용):

Prefix	용도
chores/	폴더 관리, 자잘한 정리
workspace/	개인 작업
curriculum/	학습 자료 업데이트
docs/	문서 정리
projects/	프로젝트 기능 작업
codetests/	코딩 테스트 연습



⸻

📌 4. 변경 사항 저장 (커밋하기)

(1) 변경된 파일 확인:

git status

(2) 변경 사항 스테이징:

git add .

(3) 커밋:

git commit -m "[Feature] 로그인 기능 추가"

✅ 좋은 커밋 메시지 예시:
	•	[Fix] 로그인 오류 수정
	•	[Feature] 판매글 업로드 기능 추가
	•	[Chores] 폴더 구조 정리

⸻

📌 5. 원격 저장소(GitHub)로 푸시하기

git push origin feature/작업이름

✅ 여러 브랜치를 작업 중이면 각각 푸시해야 합니다.

⸻

📌 6. Pull Request (PR) 생성하기
	1.	GitHub 저장소 접속 → “Pull Requests” 클릭
	2.	“New Pull Request” 버튼 클릭
	3.	base → main, compare → feature/작업이름 설정
	4.	작업 설명 작성 및 코드 리뷰 요청
	5.	승인되면 Merge(병합)

✅ PR을 통해 코드 리뷰 및 안정적인 병합 진행!

⸻

📌 7. 머지 후 브랜치 삭제하기

(1) main 브랜치로 이동하고 최신화:

git switch main
git pull origin main

(2) 로컬 브랜치 삭제:

git branch -d feature/작업이름

(3) 원격 브랜치 삭제:

git push origin --delete feature/작업이름

✅ 정리된 깔끔한 저장소 유지!

⸻

📌 8. 새로운 작업을 시작할 때는 항상 main 최신화!

작업 시작 전:

git switch main
git pull origin main
git switch -c feature/new-task

✅ 최신 main 기반으로 브랜치를 만들어야 충돌이 줄어듭니다!

⸻

🚀 Git 협업 워크플로우 요약

단계	명령어	설명
저장소 클론	git clone	최초 1회
새 작업 브랜치 생성	git switch -c feature/작업이름	새 브랜치 만들기
변경사항 확인	git status	수정 파일 확인
변경사항 추가	git add .	스테이징
변경사항 커밋	git commit -m "메시지"	변경 저장
원격 저장소 푸시	git push origin feature/작업이름	GitHub 업로드
PR 생성 및 병합	GitHub에서 PR 생성 후 Merge	
브랜치 삭제	git branch -d, git push origin --delete	깔끔한 정리
새로운 작업 전 최신화	git pull origin main	항상 최신 main 기준



⸻

🔥 Git 협업 시 꼭 지켜야 할 사항

✅ main 브랜치 직접 수정 금지!
✅ 작업 전 항상 git pull origin main!
✅ 커밋 메시지는 의미 있게 ([Feature], [Fix], [Chores] 등)
✅ 항상 브랜치 생성 → 작업 → PR 요청 → Merge 진행!
✅ 병합된 브랜치는 반드시 삭제!

⸻

📁 현재 협업 폴더 구조 (초기 버전)

폴더	내용
backend/	백엔드 서버 개발
frontend/	프론트엔드 화면 개발
docs/	문서 정리
test/	테스트 코드 저장
scripts/	자동화 및 유지보수 스크립트

✅ 각 폴더에는 필요 시 별도 README.md 작성 가능.
✅ 폴더 구조는 팀 논의에 따라 확장/수정 가능합니다.