# financial API
</br></br></br>
# 목차
- [회원가입 및 로그인](#회원가입-및-로그인)
</br></br></br>

# 회원가입 및 로그인
  ## 회원가입(All) : POST("/auth/signup")
  |name|required|key|description|condition|
  |--|--|--|--|--|
  |name|String||사용자이름|NN|
  |email|String|UK|로그인id|NN|
  |password|String||비밀번호|NN|
<br>

  ## 로그인(All) : POST("/auth/signin")
  |name|required|key|description|condition|
  |--|--|--|--|--|
  |email|String|UK|로그인id|NN|
  |password|String||비밀번호|NN|
<br><br><br>
  
# Users
  ## 모든 유저정보(All) : GET("/users")
  |name|required|key|description|condition|
  |--|--|--|--|--|
  |id|String|UK|id|NN|
  |name|String||이름|NN|
  |email|String||로그인id|NN|
  |count|String||투자금액||
<br>
  
  ## 해당 유저조회(All) : GET("/users/{user_id}")
  |name|required|key|description|condition|
  |--|--|--|--|--|
  |id|String|UK|id|NN|
  |name|String||이름|NN|
  |email|String||로그인id|NN|
  |count|String||투자금액||
<br><br><br>
    
# Stocks
  ## 유저 주식추가(User) : POST("/stock/add")
  |name|required|key|description|condition|
  |--|--|--|--|--|
  |name|String||주식이름|NN|
  |quantity|Double||수량|NN|
  |rating|Double||평단가|NN|


