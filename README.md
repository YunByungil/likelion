# SNS 
## 🚀 기능 목록
### 공통
- [x] BaseTimeEntity
- [x] SpringSecurity
- [x] JWT  
- [ ] Response 값 만들어서 통일
   
<hr>  

  
### USER
- [x] 회원가입 
- [x] 비밀번호 수정
  
### USER - TEST
- [x] 회원가입 테스트
- [x] 비밀번호 수정 테스트  

<hr>  

### POST - Security 적용 전
- [x] 포스트 작성
- [x] 포스트 수정
- [x] 포스트 삭제
- [x] 포스트 상세보기  
  
### POST - TEST
- [x] 포스트 작성, 수정, 삭제, 조회 (Service, controller, Repo)  
  
<hr>  
  
### Comment
- [ ] 댓글 CRUD
- [ ] 대댓글 CRUD

<hr>  

## 해결해야 될 문제들
- [x] UserApiControllerTest에서 updateUserInfo jacoco 테스트 실패
- [x] Q타입, DTO타입 jacoco에서 제외시키기.
- [x] Spring Rest Docs 적용
- [x] hasRole("USER") 추가하기
- [ ] 테스트 코드 전체 수정 => hasRole 이후로

userDetail에 구현한 내용을 기반으로 내가 getUsername 메서드에 return 값이 nickname이잖아?
그렇게 설정해놨기 때문에  @Beforeeach 에 설정한 setAuthen 어쩌구에 
user 객체를 넣어도 null 떳떤 거야. 난 회원 가입에서 nickname 설정을 아예 안 했었거든?
이거 잘 해결하자.//  
  
- [x] OAuth2 로그인을 진행했을 때, username 으로 통합하자.
- [x] '' 삭제 수정할 때, username으로 하지 말고 id로 변경하자. => 연관 관계 매핑 후 진행
- [x] TokenAuthenticationFilter 에서 토큰으로 강제 권한 User가 아닌, 가입 된 유저의 role 불러서 넣자.
