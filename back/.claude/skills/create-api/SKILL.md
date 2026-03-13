---
name: create-api
description: api를 만들 때 참고해야 될 지침
---

## api 생성 지침
- api는 interface로 작성한다.
- 이 인터페이스에서 주석은 오직 swagger에서 제공하는 명세 주석으로만 작성되어야 한다.
- 클래스 레벨에는 `@Tag(name = "[{도메인 이름} API]")`만 작성한다.
- 메서드 레벨에는 `@Operation(summary = "{메서드 요약 설명}"`을 기본으로 작성하고, 애매하거나 추가 설명이 필요한 경우에만 description 옵션을 작성한다.
- 메소드 이름은 create, read, update, delete 중 하나로 시작하고 이를 기능이라고 생각하면 된다.
- 만약 요약 읽기, 상세 읽기처럼 각 기능이 여러 개라면 메소드 이름을 기능 뒤에 설명으로 작성한다. readSummary, readDetail과 같이 작성한다.
