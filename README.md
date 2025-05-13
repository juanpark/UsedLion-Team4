
# UsedLion-Team4 | 온라인 중고 거래 플랫폼 | 멋사 백엔드 자바 15기 회고팀4 

---

**기능 요약**

중고로사자 (UsedLion)는 온라인 중고 거래 플랫폼으로, 다음과 같은 핵심 기능을 제공합니다:

- **회원가입 및 로그인**
  - 로컬 회원가입 및 로그인
  - Google OAuth2 로그인 지원

- **상품 등록 및 관리**
  - 이미지 첨부 포함 상품 등록
  - 상품 수정 및 삭제
  - 판매 상태 (판매중 / 예약중 / 판매완료) 관리

- **게시글 목록 및 상세보기**
  - 카드형 UI 게시글 리스트 (PC 및 모바일 반응형)
  - 신고 횟수에 따른 배경 색상 차등 적용
  - 댓글 및 좋아요 기능
  - 게시글 조회수 카운트

- **채팅 기능**
  - 채팅 라운지 (전체 사용자 공개 채팅)
  - 상품 게시판 기반 1:1 개인 채팅

- **검색 기능**
  - 지역별 필터링
  - 조건별 검색 및 초기화 버튼

- **프로필 페이지**
  - 유저 상세 내용 조회 및 수정    
  - 유저가 작성한 게시글, 댓글, 좋아요 내역 확인
  - 신고 횟수 및 신고 사유 모달 확인
	
⸻

**라이브 사이트**
```
https://usedlion.online
```

**소스파일 빌드 및 실행 방법**

1. 프로젝트 클론 또는 zip 파일 압축 풀기
```
git clone https://github.com/juanpark/UsedLion-Team4.git
cd test/usedlion/
```

2. 백엔드 서버 실행
	1.	application.properties 설정 확인 (DB 정보, OAuth2 등)
	2.	빌드 및 실행
```
./gradlew build
./gradlew bootRun
```

⸻

# 프로젝트 기술 스택

**Backend:** Java 21, Spring Boot 3.4.4, Spring Security, Spring Data JPA, MySQL 8

**Frontend:** HTML, CSS (Bootstrap 5), JavaScript, Thymeleaf + Layout Dialect

**Build Tool:** Gradle

**OAuth2:** Google Login

**API:** Daum Postcode API

**인프라:** Mac Mini 서버, NGINX + SSL (Let’s Encrypt), Reverse Proxy 구성
→ 접속 주소: https://usedlion.online

⸻  

# 로컬 개발 환경 세팅

**필수 설치 도구**
- Java 21 (JDK)
- MySQL 8
- Gradle (IntelliJ에서 자동 관리됨)
- IntelliJ IDEA (Ultimate or Community)
- Git

**프로젝트 클론**
```
git clone https://github.com/juanpark/UsedLion-Team4.git
cd usedlion
```
⚠️ 기본 개발 브랜치는 main이 아닌 development/juan입니다.작업은 반드시 새 브랜치를 생성하여 진행하세요:

```
git fetch origin
git checkout -b development-juan-feature origin/development/juan
```
***더 자세한 깃허브 협업 내용은 아래에 깃허브 가이드를 참고하세요**

⸻  


# MySQL 설정
- 데이터베이스 설정 파일은 별도로 정리된 파일을 참고하세요  
- 사용자명/비밀번호: 팀에서 공유된 chatadmin 계정 사용  

- application.properties 또는 application-local.properties에 입력:  
```
spring.datasource.username=chatadmin
spring.datasource.password=... (공유된 비밀번호)
```
⸻  
**MySQL DB 접근 안내**
**멋사 회고 4팀 원격 MySQL 접속 가이드 - SSH Tunnel 방식 (Windows + WSL)**

각자 로컬 환경에서 맥미니 서버의 MySQL DB에 안전하게 접속할 수 있도록 SSH 터널 방식을 설정하였습니다.  
외부에서도 마치 같은 네트워크에 있는 것처럼 접속 가능하며, 보안도 유지됩니다.  

📌 접속 정보  
- SSH 접속 대상 IP: 개별 메일로 안내  
- SSH 포트: 3307  
- SSH 사용자명: dbtunnel  
- SSH Key 파일: dbtunnel_id_rsa (개별 메일로 발송)  
- MySQL 호스트 이름: 127.0.0.1  
- MySQL 포트: 3306  
- MySQL 사용자명: chatadmin  
- MySQL 비밀번호: 개별 메일로 안내  

*필요 시 Putty 또는 직접 MySQL Workbench 클라이언트 세팅에서 TCP/IP over SSH를 활용할 수도 있지만, 현재는 WSL 기반 터널이 가장 간편하고 확실합니다.  

**SSH 클라이언트 설치**
- Mac / Linux 사용자는 기본 제공됩니다.  
- Windows 사용자는 PuTTY 대신 WSL(Windows Subsystem for Linux)이나 OpenSSH 사용을 권장합니다.  

**Windows 환경 접속 방법**  
WSL을 통한 SSH 터널 연결 (권장)  

1. WSL 설치   
- PowerShell에서 다음 명령 실행 (설치 후 재부팅 필요)  
```
wsl --install
```
- wsl 입력해 리눅스 터미널 실행  

2. SSH 키 준비  

```
// 리눅스 환경에서 ssh 키 보관할 폴더 생성 및 그 폴더로 이동
mkdir -p ~/.ssh
cd ~/.ssh

// 키를 다운로드 받은 윈도우 경로에서 리눅스 키 보관 폴더로 복사하기
cp /mnt/c/다운로드경로/dbtunnel_id_rsa ~/.ssh/

// 리눅스 환경에서 키 권한 설정하기 
chmod 600 ~/.ssh/dbtunnel_id_rsa
```
- WSL과 윈도우 경로가 다르기에 리눅스 /mnt/c/... 경로에 윈도우 c: 경로가 마운트되어 있는 부분을 활용해서 파일을 옮기자

3. SSH 터널 연결
```
ssh -i ~/.ssh/dbtunnel_id_rsa -N -T -L 3307:127.0.0.1:3306 dbtunnel@[맥미니IP]
```
- 처음 접속 시 “yes” 입력 필요할 수 있음
- 창이 멈춘 것처럼 보여도 정상 연결 상태입니다
- 이 창을 닫지 말고 유지해 주세요 (터널로 연결 되어 있는 상태입니다)

---

**로컬 MySQL Workbench 설정**

1. MySQL Connections > 새로운 연결 생성  
2. 설정:  
    - Connection Method: Standard TCP/IP
    - Hostname: 127.0.0.1
    - Port: 3307
    - Username: chatadmin
    - Password: 메일로 받은 비밀번호

3. SSH 탭은 비워두세요
   (WSL에서 이미 터널이 연결된 상태이므로 불필요)

---

💡 터미널에서 접속하고 싶다면

```
mysql -h 127.0.0.1 -P 3307 -u chatadmin -p
```

```
// DB 접속 시 쿼리 example:
USE CHATDB;
SHOW TABLES;
SELECT * FROM CHAT_MESSAGES;
```



# 팀원들을 위한 Git 사용 가이드

UsedLion-Team4 프로젝트 협업을 위해 Git 및 GitHub를 효과적으로 사용하는 방법을 안내합니다.  
브랜치를 활용하여 안정적인 코드 관리와 원활한 협업이 가능하도록 구성했습니다.

---

## 📌 1. GitHub 저장소 클론하기 (최초 1회)

- **맥**: 터미널 사용  
- **윈도우**: Git Bash 또는 GitHub CLI 사용 추천 (GUI 툴 사용도 가능)

### 진행 방법
1. 로컬 프로젝트 폴더(ex. `~/Dev/`)로 이동
2. 저장소 링크: [https://github.com/juanpark/UsedLion-Team4.git](https://github.com/juanpark/UsedLion-Team4.git)
3. "Code" 클릭 → Clone 방법 선택

🔹 HTTPS
```bash
git clone https://github.com/juanpark/UsedLion-Team4.git
```

🔹 SSH
```bash
git clone git@github.com:juanpark/UsedLion-Team4.git
```

✅ 클론 완료!

---

## 📌 2. main에서 직접 작업하지 마세요!

❌ 금지된 방법:
```bash
git add .
git commit -m "수정함"
git push origin main   # 🚨 금지
```
👉 main은 항상 안정적인 상태를 유지해야 합니다.

---

## 📌 3. 새로운 브랜치에서 작업하기

### 브랜치 생성
```bash
git switch -c feature/작업이름
```

### 브랜치 관련 명령어
```bash
git branch            # 모든 브랜치 목록
git switch 브랜치명    # 브랜치 이동
git switch -           # 이전 브랜치로 이동
```

### 브랜치 이름 예시
```bash
feature/add-login
feature/improve-ui
bugfix/fix-login-error
```

### 브랜치 네이밍 규칙

| Prefix | 용도 |
|:---|:---|
| chores/ | 폴더 정리 |
| workspace/ | 개인 작업 |
| curriculum/ | 학습 자료 업데이트 |
| docs/ | 문서 정리 |
| projects/ | 프로젝트 작업 |
| codetests/ | 코딩 테스트 |

---

## 📌 4. 변경 사항 저장 (커밋하기)

### 변경된 파일 확인
```bash
git status
```

### 스테이징
```bash
git add .
```

### 커밋
```bash
git commit -m "[Feature] 로그인 기능 추가"
```

✅ 커밋 메시지 예시:  
- `[Fix] 로그인 오류 수정`  
- `[Feature] 판매글 업로드 기능 추가`  
- `[Chores] 폴더 구조 정리`

---

## 📌 5. 원격 저장소로 푸시하기

```bash
git push origin feature/작업이름
```

---

## 📌 6. Pull Request (PR) 생성하기

1. GitHub 저장소 → Pull Requests 클릭
2. "New Pull Request" 클릭
3. base: main / compare: feature/작업이름 설정
4. 작업 설명 작성, 팀원에게 리뷰 요청
5. 승인 후 Merge

---

## 📌 7. 머지 후 브랜치 삭제하기

### main 최신화
```bash
git switch main
git pull origin main
```

### 로컬 브랜치 삭제
```bash
git branch -d feature/작업이름
```

### 원격 브랜치 삭제
```bash
git push origin --delete feature/작업이름
```

---

## 📌 8. 새로운 작업을 시작할 때 항상 main 최신화!

```bash
git switch main
git pull origin main
git switch -c feature/new-task
```

---

# 🚀 Git 협업 워크플로우 요약

| 단계 | 명령어 | 설명 |
|:----|:----|:----|
| 저장소 클론 | `git clone` | 로컬에 저장소 복제 |
| 새 브랜치 생성 | `git switch -c feature/작업이름` | 새 작업 브랜치 생성 |
| 변경사항 확인 | `git status` | 수정 파일 확인 |
| 변경사항 추가 | `git add .` | 스테이징 |
| 변경사항 커밋 | `git commit -m "메시지"` | 변경 저장 |
| 원격 저장소 푸시 | `git push origin feature/작업이름` | GitHub 업로드 |
| PR 생성 및 병합 | GitHub에서 PR 생성 후 Merge |
| 브랜치 삭제 | `git branch -d`, `git push origin --delete` | 병합 완료 후 삭제 |
| 새로운 작업 시작 전 최신화 | `git pull origin main` | 항상 최신 main 기준 |

---

# 🔥 Git 협업 시 주의할 점

✅ main 브랜치 직접 수정 금지  
✅ 작업 전 항상 최신 main pull  
✅ 의미 있는 커밋 메시지 작성 (`[Feature]`, `[Fix]`, `[Chores]` 등)  
✅ 브랜치 → PR → Merge 순서로 협업  
✅ 병합 완료 후 브랜치 삭제

---

# 📁 현재 협업 폴더 구조

| 폴더 | 내용 |
|:---|:---|
| backend/ | 백엔드 서버 개발 |
| frontend/ | 프론트엔드 화면 개발 |
| docs/ | 문서 정리 |
| test/ | 테스트 코드 저장 |
| scripts/ | 스크립트 자동화 |

✅ 각 폴더에 필요한 경우 별도 README.md 추가 가능  
✅ 구조는 필요시 팀 논의로 확장/수정 가능

---
