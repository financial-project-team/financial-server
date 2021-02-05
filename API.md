# financial API

# 목차
- [회원가입 및 로그인](#회원가입-및-로그인)
</br>

# 회원가입 및 로그인
  ## 회원가입 : POST("/auth/signup")
  |name|required|key|description|condition|
  |--|--|--|--|--|
  |name|String||사용자이름|NN|
  |email|String|UK|id|NN|
  |password|String||password|NN|
  
  ## 로그인 : POST("/auth/signin")
  |name|required|key|description|condition|
  |--|--|--|--|--|
  |email|String|UK|id|NN|
  |password|String||password|NN|
