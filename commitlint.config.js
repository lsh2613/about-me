module.exports = {
    "extends": ["@commitlint/config-conventional"],
    "rules": {
        "type-enum": [
            2,
            "always",
            [
                "feat", // 기능 추가/수정
                "design", // css 등 사용자 ui 디자인 변경
                "style", // 코드 포맷팅, 세미콜론 누락 등 코드 변경이 없는 경우
                "refactor", // 리팩터링(기능 변화 없이 구조 개선)
                "test", // 테스트 코드 추가/수정
                "rename", // 파일/코드/변수명/메소드명 등의 이름 변경
                "remove", // 파일/코드 제거
                "fix", // 버그 수정
                "docs", // 문서 추가/수정
                "comment", // 주석 추가/수정
                "chore" // 빌드, 설정, 기타 잡무
            ]
        ],
        'scope-enum': [
            2,
            'always',
            [
                'all', // 모든 영역에 영향을 미치는 변경
                'front', // 프론트엔드 관련 변경
                'back' // 백엔드 관련 변경
            ]
        ],
        "header-max-length": [2, "always", 200],
        "body-max-line-length": [0],
        "subject-case": [0],
        "subject-full-stop": [2, "never", "."],
        "body-leading-blank": [2, "always"],
        "footer-leading-blank": [2, "always"]
    }
};