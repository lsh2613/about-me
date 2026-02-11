## docker-compose.yml의 ${} 변수 치환 시 주의사항

env_file은 컨테이너 내부의 환경 변수를 설정해주는 것이지 yml의 ${}을 치환해주지 않는다

```yml
# test.env
  USER=hello

# docker-compose.yml
services:
  test:
    env_file:
      - test.env
    environment:
      USER: ${USER:-root} # 치환 실패, hello가 아닌 root 할당
```

만약, docker-compose.yml과 같은 계층에 .env가 있다면 ${} 치환이 된다.

```yml
    # test.env
      USER=hello

    # docker-compose.yml
    services:
      test:
        environment:
          USER: ${USER:-root} # 치환 성공, hello가 할당
```

만약 다른 계층 혹은 다른 이름의 env를 관리하고 있다면 docker-compose의 --env-file 옵션을 사용하여 env 파일을 지정해주면 된다.

`docker-compose --env-file {env경로} -f docker-compose-db.yml up`
