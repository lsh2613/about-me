# PRD (Product Requirements Document) - About Me 포트폴리오 웹사이트

## 1. 프로젝트 개요

### 1.1 프로젝트명
**About Me** - 개인 포트폴리오 및 경험 공유 플랫폼

### 1.2 프로젝트 목적
본 프로젝트는 개인의 경력, 기술 스택, 프로젝트 경험, 그리고 다양한 경험들을 체계적으로 정리하고 공유할 수 있는 개인 포트폴리오 웹사이트를 구축하는 것을 목적으로 합니다. 특히, RAG(Retrieval-Augmented Generation) 시스템을 활용한 AI 챗봇을 통해 방문자가 더욱 편리하게 정보에 접근할 수 있도록 하는 것이 핵심 차별점입니다.

### 1.3 타겟 사용자
- **주 사용자 (ADMIN)**: 포트폴리오 소유자 본인
- **부 사용자 (일반 방문자)**: 채용 담당자, 개발자 커뮤니티 멤버, 협업 파트너 등

### 1.4 핵심 가치 제안
1. **체계적인 정보 구조화**: 이력서, 포트폴리오, 경험 공유를 하나의 플랫폼에서 관리
2. **지능형 정보 접근**: AI 챗봇을 통한 자연어 기반 정보 탐색
3. **관리자 중심 설계**: 간편한 컨텐츠 관리 및 접근 권한 제어
4. **최적화된 사용자 경험**: 반응형 디자인, 다크모드, 접근성, SEO 최적화

---

## 2. 핵심 기능 명세

### 2.1 자기소개 기능
개인의 전문성과 배경을 효과적으로 전달하기 위한 이력서 및 자기소개서 관리 기능

**주요 기능:**
- 이력서 작성 및 관리
- 자기소개서 작성 및 관리
- 실시간 글자 수 카운팅

### 2.2 포트폴리오 기능
프로젝트 경험과 기술 역량을 시각적으로 효과적으로 보여주는 기능

**주요 기능:**
- 프로젝트 경험 등록 및 관리
- 기술 스택 태그 시스템
- 리치 텍스트 에디터를 통한 상세 설명 작성

### 2.3 포스팅 기능
기술적 인사이트, 문제 해결 경험 등을 자유롭게 공유하는 블로그형 기능

**주요 기능:**
- 블로그 포스트 작성 및 관리
- 리치 텍스트 에디터 지원
- 작성일 기반 정렬 및 조회

### 2.4 채팅 기능
RAG 시스템 기반 AI 챗봇을 통한 지능형 정보 접근 기능

**주요 기능:**
- 자기소개, 포트폴리오, 포스팅 데이터 기반 응답
- 전역 접근 가능한 플로팅 채팅 아이콘
- 모달 기반 채팅 인터페이스

---

## 3. 세부 기능 요구사항

### 3.1 인증 및 권한 관리

#### 3.1.1 로그인 시스템
**요구사항:**
- Basic Auth 방식을 사용한 관리자 인증
- 회원가입 기능은 제공하지 않음 (단일 관리자)
- 세션 기반 인증 상태 유지

**기술 명세:**
- 인증 방식: HTTP Basic Authentication
- 세션 관리: JWT 또는 서버 사이드 세션
- 보안: HTTPS 필수, 비밀번호 암호화 저장

#### 3.1.2 권한 관리
**요구사항:**
- 모든 데이터의 등록/수정/삭제는 ADMIN만 가능
- 일반 사용자는 조회 권한만 보유
- 관리자는 각 기능별 접근 권한을 On/Off로 제어 가능

**기능별 접근 제어:**
| 기능 | ADMIN | 일반 사용자 |
|------|-------|-------------|
| 자기소개 | CRUD | Read (권한 설정 시) |
| 포트폴리오 | CRUD | Read (권한 설정 시) |
| 포스팅 | CRUD | Read (권한 설정 시) |
| 채팅 | CRUD | Read/Write (권한 설정 시) |

**관리자 설정 페이지:**
- 각 기능별 공개/비공개 토글 스위치
- 실시간 적용
- 비공개 시 일반 사용자에게 "준비 중입니다" 메시지 표시

---

### 3.2 자기소개 기능 상세

#### 3.2.1 이력서 (Resume)

**데이터 구조:**
```typescript
interface Resume {
  personalInfo?: {
    name: string;
    email: string;
    location: string;
  };
  skills?: string[];
  education?: Array<{
    institution: string;
    degree: string;
    period: string;
    major?: string;
  }>;
  training?: Array<{
    name: string;
    institution: string;
    period: string;
    description?: string;
  }>;
  certifications?: Array<{
    name: string;
    issuer: string;
    date: string;
    id?: string;
  }>;
  awards?: Array<{
    name: string;
    issuer: string;
    date: string;
    description?: string;
  }>;
}
```

**UI/UX 요구사항:**
- 섹션별로 구분된 카드 레이아웃
- 각 섹션은 추가/수정/삭제 가능
- 드래그 앤 드롭으로 순서 변경 가능
- 비어있는 섹션은 자동으로 숨김 처리

**폼 검증:**
- 이메일 형식 검증
- 날짜 형식 검증
- 필수 필드 없음 (모두 선택적)

#### 3.2.2 자기소개서 (Cover Letter)

**데이터 구조:**
```typescript
interface CoverLetter {
  sections: Array<{
    id: string;
    title: string;
    content: string;
    characterCount: number;
    createdAt: Date;
    updatedAt: Date;
  }>;
}
```

**UI/UX 요구사항:**
- 여러 주제의 자기소개서 작성 가능
- 실시간 글자 수 표시 (공백 포함/미포함 옵션)
- 자동 저장 기능
- 텍스트 에디터: 기본 포맷팅 지원 (볼드, 이탤릭, 리스트 등)

**글자 수 카운팅:**
- 실시간 업데이트
- 공백 포함/미포함 선택 가능
- 목표 글자 수 설정 가능
- 진행률 바 표시

---

### 3.3 포트폴리오 기능 상세

#### 3.3.1 프로젝트 경험

**데이터 구조:**
```typescript
interface Project {
  id: string;
  name: string;
  period: {
    startDate: Date | string;
    endDate?: Date | string;
    isOngoing: boolean;
  };
  techStack: string[];
  links: Array<{
    type: 'website' | 'github' | 'demo' | 'other';
    url: string;
    label: string;
  }>;
  description: string; // HTML 형식
  thumbnail?: string;
  createdAt: Date;
  updatedAt: Date;
}
```

**기간 입력 방식:**
1. **캘린더 선택**
   - Date Picker UI 제공
   - 시작일과 종료일 선택
   - 미래 날짜 선택 불가 (시작일 기준)

2. **텍스트 직접 입력**
   - 예: "2023.03 - 2023.12"
   - 자유 형식 허용

3. **진행 중 프로젝트**
   - "현재 진행 중" 체크박스
   - 체크 시 종료일 입력 비활성화
   - 화면에 "진행 중" 배지 표시

**기술 스택 관리:**
- 자동완성 기능 지원
- 태그 형태로 표시
- 색상 코드 지정 가능
- 드래그로 순서 변경
- 클릭 시 해당 기술 스택 사용 프로젝트 필터링

**링크 관리:**
- 링크 타입별 아이콘 표시
- 새 탭에서 열기
- 유효성 검증 (URL 형식)
- 여러 개의 링크 등록 가능

**프로젝트 설명 에디터:**
- CKEditor 5 사용
- 이미지 업로드 지원 (드래그 앤 드롭)
- 이미지 크기 조절
- 코드 블록 삽입
- 링크 삽입
- 테이블 삽입
- HTML 소스 편집 모드

**UI/UX:**
- 카드 그리드 레이아웃
- 호버 시 상세 정보 프리뷰
- 클릭 시 모달 또는 상세 페이지 표시
- 기술 스택별 필터링
- 날짜순/이름순 정렬

---

### 3.4 포스팅 기능 상세

#### 3.4.1 블로그 포스트

**데이터 구조:**
```typescript
interface Post {
  id: string;
  title: string;
  content: string; // HTML 형식
  tags?: string[];
  thumbnail?: string;
  createdAt: Date;
  updatedAt: Date;
  views?: number;
}
```

**필수 필드:**
- 제목 (title)
- 내용 (content)

**선택 필드:**
- 태그
- 썸네일 이미지
- 조회수

**콘텐츠 에디터:**
- CKEditor 5 사용
- 포트폴리오와 동일한 에디터 기능
- 마크다운 지원 옵션
- 이미지 드래그 앤 드롭
- 이미지 최적화 (자동 압축)

**UI/UX:**
- 블로그 리스트 페이지
  - 카드 형식 또는 리스트 형식 선택
  - 작성일 표시
  - 태그 표시
  - 조회수 표시 (선택)
  
- 블로그 상세 페이지
  - 목차 자동 생성 (H2, H3 태그 기반)
  - 공유하기 버튼
  - 이전/다음 포스트 네비게이션

**검색 및 필터:**
- 제목/내용 검색
- 태그별 필터링
- 날짜별 정렬
- 조회수별 정렬

---

### 3.5 채팅 기능 상세

#### 3.5.1 RAG 기반 챗봇

**데이터 소스:**
- 자기소개 데이터
- 포트폴리오 데이터
- 포스팅 데이터

**기술 아키텍처:**
```
사용자 질문 → 벡터 검색 (RAG) → 관련 문서 검색 → LLM 응답 생성 → 사용자에게 표시
```

**RAG 시스템 구성:**
1. **데이터 인덱싱**
   - 자기소개, 포트폴리오, 포스팅 데이터를 벡터 DB에 저장
   - 주기적 업데이트 (데이터 변경 시 자동)

2. **질문 처리**
   - 사용자 질문을 벡터로 변환
   - 유사도 기반 관련 문서 검색
   - 컨텍스트와 함께 LLM에 전달

3. **응답 생성**
   - 검색된 문서를 기반으로 답변 생성
   - 출처 표시 (어느 섹션에서 가져온 정보인지)

**UI/UX:**
- **플로팅 채팅 아이콘**
  - 화면 우측 하단 고정
  - 모든 페이지에서 접근 가능
  - 읽지 않은 메시지 알림 표시
  - 애니메이션 효과

- **채팅 모달**
  - 아이콘 클릭 시 모달 창 오픈
  - 크기: 모바일 전체화면, 데스크톱 400x600px
  - 위치: 우측 하단에서 팝업
  - 닫기 버튼

- **채팅 인터페이스**
  - 메시지 입력창
  - 전송 버튼
  - 메시지 히스토리
  - 타이핑 인디케이터
  - 시간 표시

**대화 기능:**
- 실시간 응답
- 마크다운 렌더링
- 코드 블록 하이라이팅
- 링크 자동 감지
- 이미지 표시 지원

**예시 질문:**
- "어떤 기술 스택을 사용하나요?"
- "최근 진행한 프로젝트는 무엇인가요?"
- "Java 개발 경험이 있나요?"
- "학력이 어떻게 되나요?"

---

## 4. 기술 스택 및 아키텍처

### 4.1 프론트엔드

**핵심 기술:**
- **프레임워크**: React 18+
- **언어**: TypeScript
- **상태 관리**: Redux Toolkit 또는 Zustand
- **라우팅**: React Router v6
- **스타일링**: 
  - Tailwind CSS (유틸리티 우선)
  - Styled Components 또는 Emotion (컴포넌트 스타일링)
- **UI 컴포넌트**: 
  - Headless UI 또는 Radix UI (접근성)
  - Custom 디자인 시스템

**주요 라이브러리:**
- **에디터**: CKEditor 5
- **폼 관리**: React Hook Form
- **유효성 검증**: Zod 또는 Yup
- **날짜 처리**: date-fns
- **HTTP 클라이언트**: Axios
- **애니메이션**: Framer Motion
- **드래그 앤 드롭**: @dnd-kit/core

**빌드 도구:**
- **번들러**: Vite 또는 Create React App
- **패키지 매니저**: npm 또는 yarn
- **린터**: ESLint
- **포매터**: Prettier

### 4.2 백엔드

**핵심 기술:**
- **프레임워크**: Spring Boot 3.x
- **언어**: Java 17+ 또는 Kotlin
- **데이터베이스**: PostgreSQL 또는 MySQL
- **ORM**: Spring Data JPA
- **인증**: Spring Security

**주요 기능:**
- RESTful API
- JWT 인증
- 파일 업로드 관리
- 이미지 최적화

### 4.3 AI/RAG 시스템

**핵심 기술:**
- **벡터 DB**: Pinecone, Weaviate, 또는 Chroma
- **임베딩 모델**: OpenAI Embeddings 또는 오픈소스 모델
- **LLM**: OpenAI GPT-4, GPT-3.5, 또는 오픈소스 LLM
- **RAG 프레임워크**: LangChain 또는 LlamaIndex

**데이터 파이프라인:**
1. 데이터 변경 감지
2. 텍스트 추출 및 전처리
3. 청크 분할
4. 벡터 임베딩 생성
5. 벡터 DB에 저장

### 4.4 인프라 및 배포

**호스팅:**
- **프론트엔드**: Vercel, Netlify, 또는 AWS S3 + CloudFront
- **백엔드**: AWS EC2, ECS, 또는 Heroku
- **데이터베이스**: AWS RDS 또는 관리형 PostgreSQL

**CI/CD:**
- GitHub Actions
- 자동 테스트
- 자동 배포

**모니터링:**
- Sentry (에러 추적)
- Google Analytics (사용자 분석)

---

## 5. UI/UX 디자인 가이드라인

### 5.1 디자인 시스템

#### 5.1.1 컬러 팔레트

**라이트 모드:**
```css
--primary: #3B82F6;      /* 주요 액션 버튼, 링크 */
--secondary: #8B5CF6;    /* 보조 강조 */
--success: #10B981;      /* 성공 메시지 */
--warning: #F59E0B;      /* 경고 */
--error: #EF4444;        /* 에러 */
--background: #FFFFFF;   /* 배경 */
--surface: #F9FAFB;      /* 카드 배경 */
--text-primary: #111827; /* 주요 텍스트 */
--text-secondary: #6B7280; /* 보조 텍스트 */
--border: #E5E7EB;       /* 테두리 */
```

**다크 모드:**
```css
--primary: #60A5FA;
--secondary: #A78BFA;
--success: #34D399;
--warning: #FBBF24;
--error: #F87171;
--background: #111827;
--surface: #1F2937;
--text-primary: #F9FAFB;
--text-secondary: #9CA3AF;
--border: #374151;
```

#### 5.1.2 타이포그래피

**폰트 패밀리:**
- 한글: Pretendard, Noto Sans KR
- 영문: Inter, SF Pro Display
- 코드: Fira Code, JetBrains Mono

**폰트 크기:**
```css
--text-xs: 0.75rem;    /* 12px */
--text-sm: 0.875rem;   /* 14px */
--text-base: 1rem;     /* 16px */
--text-lg: 1.125rem;   /* 18px */
--text-xl: 1.25rem;    /* 20px */
--text-2xl: 1.5rem;    /* 24px */
--text-3xl: 1.875rem;  /* 30px */
--text-4xl: 2.25rem;   /* 36px */
```

#### 5.1.3 간격 시스템

```css
--spacing-1: 0.25rem;  /* 4px */
--spacing-2: 0.5rem;   /* 8px */
--spacing-3: 0.75rem;  /* 12px */
--spacing-4: 1rem;     /* 16px */
--spacing-5: 1.25rem;  /* 20px */
--spacing-6: 1.5rem;   /* 24px */
--spacing-8: 2rem;     /* 32px */
--spacing-10: 2.5rem;  /* 40px */
--spacing-12: 3rem;    /* 48px */
--spacing-16: 4rem;    /* 64px */
```

#### 5.1.4 그림자

```css
--shadow-sm: 0 1px 2px 0 rgb(0 0 0 / 0.05);
--shadow-md: 0 4px 6px -1px rgb(0 0 0 / 0.1);
--shadow-lg: 0 10px 15px -3px rgb(0 0 0 / 0.1);
--shadow-xl: 0 20px 25px -5px rgb(0 0 0 / 0.1);
```

#### 5.1.5 모서리 반경

```css
--radius-sm: 0.25rem;  /* 4px */
--radius-md: 0.375rem; /* 6px */
--radius-lg: 0.5rem;   /* 8px */
--radius-xl: 0.75rem;  /* 12px */
--radius-full: 9999px;
```

### 5.2 레이아웃 구조

#### 5.2.1 전역 레이아웃

**헤더 (Header):**
- 높이: 64px (모바일), 72px (데스크톱)
- 로고 (좌측)
- 네비게이션 메뉴 (중앙 또는 우측)
- 다크모드 토글
- 로그인 버튼 (우측)
- 고정 위치 (스크롤 시 숨김/표시 옵션)

**네비게이션 메뉴:**
- 자기소개
- 포트폴리오
- 포스팅
- 관리자 전용: 설정

**풋터 (Footer):**
- 소셜 미디어 링크
- 연락처
- 저작권 정보
- 간단한 설명

**메인 컨테이너:**
- 최대 너비: 1280px
- 좌우 여백: 16px (모바일), 24px (태블릿), 48px (데스크톱)

#### 5.2.2 페이지별 레이아웃

**홈 페이지:**
- 히어로 섹션 (프로필 사진, 이름, 한 줄 소개)
- 주요 기능 소개 카드
- 최근 프로젝트 미리보기
- 최근 포스트 미리보기

**자기소개 페이지:**
- 탭 네비게이션 (이력서 / 자기소개서)
- 이력서: 섹션별 카드 레이아웃
- 자기소개서: 주제별 아코디언 또는 탭

**포트폴리오 페이지:**
- 필터 바 (기술 스택, 날짜)
- 프로젝트 그리드 (3열 - 데스크톱, 2열 - 태블릿, 1열 - 모바일)
- 프로젝트 카드 (썸네일, 제목, 기간, 기술 스택)

**포스팅 페이지:**
- 검색 바
- 태그 필터
- 포스트 리스트 (카드 형식)
- 페이지네이션

**관리자 페이지:**
- 사이드바 네비게이션
- 컨텐츠 영역 (CRUD 인터페이스)

### 5.3 반응형 디자인

**브레이크포인트:**
```css
--breakpoint-sm: 640px;   /* 모바일 */
--breakpoint-md: 768px;   /* 태블릿 */
--breakpoint-lg: 1024px;  /* 데스크톱 */
--breakpoint-xl: 1280px;  /* 대형 데스크톱 */
--breakpoint-2xl: 1536px; /* 초대형 데스크톱 */
```

**모바일 우선 접근:**
- 기본 스타일은 모바일
- 미디어 쿼리로 더 큰 화면 대응
- 터치 친화적 인터랙션
- 햄버거 메뉴 (모바일)

**태블릿:**
- 2열 그리드 레이아웃
- 확장된 네비게이션
- 적절한 간격 조정

**데스크톱:**
- 3열 그리드 레이아웃
- 풀 네비게이션 메뉴
- 호버 효과
- 마우스 인터랙션 최적화

### 5.4 애니메이션 및 인터랙션

**페이지 전환:**
- Fade in/out
- 지속 시간: 200-300ms
- Easing: ease-in-out

**버튼 호버:**
- 배경색 변화
- 약간의 확대 (scale: 1.02)
- 그림자 강화

**카드 호버:**
- 상승 효과 (translateY: -4px)
- 그림자 강화
- 테두리 색상 변화

**로딩 상태:**
- 스켈레톤 UI
- 스피너
- 진행률 바

**스크롤 애니메이션:**
- Fade in on scroll
- Parallax 효과 (선택적)

---

## 6. 비기능적 요구사항

### 6.1 성능 요구사항

**로딩 시간:**
- 초기 페이지 로드: 3초 이내
- 페이지 전환: 1초 이내
- API 응답 시간: 500ms 이내

**최적화 전략:**
- 코드 스플리팅 (React.lazy)
- 이미지 최적화
  - WebP 형식 사용
  - Lazy loading
  - 반응형 이미지 (srcset)
- 번들 크기 최소화
- CDN 활용
- 서버 사이드 렌더링 (선택적)

**캐싱:**
- 브라우저 캐싱
- Service Worker (PWA)
- API 응답 캐싱

### 6.2 SEO 최적화

**메타 태그:**
```html
<meta name="description" content="...">
<meta name="keywords" content="...">
<meta property="og:title" content="...">
<meta property="og:description" content="...">
<meta property="og:image" content="...">
<meta property="og:url" content="...">
<meta name="twitter:card" content="...">
```

**구조화된 데이터:**
- JSON-LD 형식
- Person Schema
- Article Schema (블로그)

**사이트맵:**
- sitemap.xml 생성
- robots.txt 설정

**URL 구조:**
- 의미 있는 URL
- 한글 URL 인코딩 고려
- 예시:
  - `/about` - 자기소개
  - `/portfolio` - 포트폴리오
  - `/posts` - 포스팅 목록
  - `/posts/:slug` - 포스트 상세

### 6.3 접근성 (A11y)

**WCAG 2.1 Level AA 준수:**

**키보드 네비게이션:**
- 모든 인터랙티브 요소 접근 가능
- Tab 순서 논리적 구성
- Focus indicator 명확하게 표시
- Skip to main content 링크

**스크린 리더 지원:**
- 시맨틱 HTML 사용
- ARIA 레이블 적절히 사용
- alt 텍스트 제공
- 헤딩 계층 구조 준수

**색상 대비:**
- 텍스트 대비율 4.5:1 이상
- 대형 텍스트 3:1 이상
- 색상만으로 정보 전달 금지

**폼 접근성:**
- 레이블 명확하게 연결
- 에러 메시지 명확하게 전달
- 필수 필드 표시

### 6.4 보안 요구사항

**인증 보안:**
- HTTPS 강제
- 비밀번호 해싱 (bcrypt)
- JWT 토큰 보안 저장
- CSRF 보호
- XSS 방지

**데이터 보안:**
- 입력 유효성 검증
- SQL 인젝션 방지
- 파일 업로드 검증
- 민감 정보 암호화

**API 보안:**
- Rate limiting
- CORS 설정
- API 키 관리

### 6.5 호환성

**브라우저 지원:**
- Chrome (최신 2개 버전)
- Firefox (최신 2개 버전)
- Safari (최신 2개 버전)
- Edge (최신 2개 버전)

**디바이스 지원:**
- 데스크톱
- 태블릿
- 모바일

**해상도 지원:**
- 320px ~ 2560px

---

## 7. 데이터 관리

### 7.1 데이터베이스 스키마

**users (관리자)**
```sql
CREATE TABLE users (
  id UUID PRIMARY KEY,
  username VARCHAR(50) UNIQUE NOT NULL,
  password_hash VARCHAR(255) NOT NULL,
  email VARCHAR(100),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

**settings (전역 설정)**
```sql
CREATE TABLE settings (
  id UUID PRIMARY KEY,
  key VARCHAR(100) UNIQUE NOT NULL,
  value TEXT,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

**resume (이력서)**
```sql
CREATE TABLE resume (
  id UUID PRIMARY KEY,
  user_id UUID REFERENCES users(id),
  personal_info JSONB,
  skills JSONB,
  education JSONB,
  training JSONB,
  certifications JSONB,
  awards JSONB,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

**cover_letters (자기소개서)**
```sql
CREATE TABLE cover_letters (
  id UUID PRIMARY KEY,
  user_id UUID REFERENCES users(id),
  title VARCHAR(255) NOT NULL,
  content TEXT NOT NULL,
  character_count INTEGER,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

**projects (프로젝트)**
```sql
CREATE TABLE projects (
  id UUID PRIMARY KEY,
  user_id UUID REFERENCES users(id),
  name VARCHAR(255) NOT NULL,
  start_date VARCHAR(100),
  end_date VARCHAR(100),
  is_ongoing BOOLEAN DEFAULT FALSE,
  tech_stack JSONB,
  links JSONB,
  description TEXT,
  thumbnail VARCHAR(500),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

**posts (포스팅)**
```sql
CREATE TABLE posts (
  id UUID PRIMARY KEY,
  user_id UUID REFERENCES users(id),
  title VARCHAR(255) NOT NULL,
  content TEXT NOT NULL,
  tags JSONB,
  thumbnail VARCHAR(500),
  views INTEGER DEFAULT 0,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

**chat_messages (채팅 메시지)**
```sql
CREATE TABLE chat_messages (
  id UUID PRIMARY KEY,
  session_id VARCHAR(255),
  message TEXT NOT NULL,
  sender VARCHAR(50), -- 'user' or 'bot'
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 7.2 파일 저장

**이미지 저장:**
- 로컬 저장소 또는 AWS S3
- 경로 구조: `/uploads/{year}/{month}/{filename}`
- 파일명: UUID + 확장자

**이미지 처리:**
- 업로드 시 자동 리사이징
- 썸네일 생성
- WebP 변환

---

## 8. API 명세

### 8.1 인증 API

**로그인**
```
POST /api/auth/login
Request: { username, password }
Response: { token, user }
```

**로그아웃**
```
POST /api/auth/logout
Response: { message }
```

**인증 확인**
```
GET /api/auth/verify
Response: { user }
```

### 8.2 설정 API

**전역 설정 조회**
```
GET /api/settings
Response: { settings }
```

**전역 설정 업데이트** (ADMIN only)
```
PUT /api/settings
Request: { key, value }
Response: { setting }
```

### 8.3 이력서 API

**이력서 조회**
```
GET /api/resume
Response: { resume }
```

**이력서 업데이트** (ADMIN only)
```
PUT /api/resume
Request: { resume }
Response: { resume }
```

### 8.4 자기소개서 API

**자기소개서 목록 조회**
```
GET /api/cover-letters
Response: { coverLetters[] }
```

**자기소개서 생성** (ADMIN only)
```
POST /api/cover-letters
Request: { title, content }
Response: { coverLetter }
```

**자기소개서 수정** (ADMIN only)
```
PUT /api/cover-letters/:id
Request: { title, content }
Response: { coverLetter }
```

**자기소개서 삭제** (ADMIN only)
```
DELETE /api/cover-letters/:id
Response: { message }
```

### 8.5 프로젝트 API

**프로젝트 목록 조회**
```
GET /api/projects
Query: ?techStack=react&sortBy=date
Response: { projects[] }
```

**프로젝트 상세 조회**
```
GET /api/projects/:id
Response: { project }
```

**프로젝트 생성** (ADMIN only)
```
POST /api/projects
Request: { project }
Response: { project }
```

**프로젝트 수정** (ADMIN only)
```
PUT /api/projects/:id
Request: { project }
Response: { project }
```

**프로젝트 삭제** (ADMIN only)
```
DELETE /api/projects/:id
Response: { message }
```

### 8.6 포스팅 API

**포스트 목록 조회**
```
GET /api/posts
Query: ?page=1&limit=10&tag=react
Response: { posts[], total, page, limit }
```

**포스트 상세 조회**
```
GET /api/posts/:id
Response: { post }
```

**포스트 생성** (ADMIN only)
```
POST /api/posts
Request: { title, content, tags }
Response: { post }
```

**포스트 수정** (ADMIN only)
```
PUT /api/posts/:id
Request: { title, content, tags }
Response: { post }
```

**포스트 삭제** (ADMIN only)
```
DELETE /api/posts/:id
Response: { message }
```

### 8.7 채팅 API

**채팅 메시지 전송**
```
POST /api/chat
Request: { message, sessionId }
Response: { reply, sources }
```

**채팅 히스토리 조회**
```
GET /api/chat/history/:sessionId
Response: { messages[] }
```

### 8.8 파일 업로드 API

**이미지 업로드**
```
POST /api/upload/image
Request: FormData (file)
Response: { url, filename }
```

---

## 9. 개발 단계 및 마일스톤

### Phase 1: 기반 구축 (2-3주)
- [ ] 프로젝트 초기 설정
- [ ] 디자인 시스템 구축
- [ ] 인증 시스템 구현
- [ ] 기본 레이아웃 구현
- [ ] 다크모드 구현

### Phase 2: 핵심 기능 구현 (4-5주)
- [ ] 자기소개 기능 구현
- [ ] 포트폴리오 기능 구현
- [ ] 포스팅 기능 구현
- [ ] CKEditor 통합
- [ ] 파일 업로드 기능

### Phase 3: AI 챗봇 구현 (3-4주)
- [ ] RAG 시스템 설계
- [ ] 벡터 DB 설정
- [ ] 데이터 인덱싱 파이프라인
- [ ] 챗봇 UI 구현
- [ ] 챗봇 백엔드 API 구현

### Phase 4: 최적화 및 테스트 (2-3주)
- [ ] 성능 최적화
- [ ] SEO 최적화
- [ ] 접근성 테스트
- [ ] 크로스 브라우저 테스트
- [ ] 반응형 디자인 테스트

### Phase 5: 배포 및 모니터링 (1-2주)
- [ ] CI/CD 파이프라인 설정
- [ ] 프로덕션 배포
- [ ] 모니터링 설정
- [ ] 문서화

---

## 10. 성공 지표 (KPI)

### 10.1 사용자 경험 지표
- **페이지 로드 시간**: < 3초
- **Time to Interactive**: < 5초
- **Lighthouse 점수**: > 90점
- **모바일 친화성**: 100점

### 10.2 기능 사용 지표
- **채팅 사용률**: 방문자의 30% 이상
- **평균 세션 시간**: > 2분
- **바운스율**: < 40%
- **페이지뷰**: 평균 3페이지 이상

### 10.3 기술 지표
- **API 응답 시간**: 평균 < 500ms
- **에러율**: < 1%
- **가동 시간**: > 99.9%

---

## 11. 위험 요소 및 대응 방안

### 11.1 기술적 위험

**RAG 시스템 성능**
- 위험: 응답 속도 저하, 부정확한 답변
- 대응: 캐싱 전략, 프롬프트 최적화, 폴백 응답 준비

**이미지 업로드 용량**
- 위험: 서버 저장 공간 부족
- 대응: 클라우드 스토리지 사용, 이미지 압축, 용량 제한

**브라우저 호환성**
- 위험: 구형 브라우저 지원 이슈
- 대응: Polyfill 사용, 점진적 향상, 기능 감지

### 11.2 보안 위험

**인증 취약점**
- 위험: 무차별 대입 공격
- 대응: Rate limiting, 계정 잠금, CAPTCHA

**XSS 공격**
- 위험: 사용자 입력 기반 스크립트 실행
- 대응: 입력 검증, 출력 이스케이핑, CSP 헤더

### 11.3 운영 위험

**데이터 손실**
- 위험: 서버 장애로 인한 데이터 손실
- 대응: 정기적 백업, 이중화, 트랜잭션 관리

**트래픽 급증**
- 위험: 서버 다운
- 대응: 오토 스케일링, CDN, 로드 밸런싱

---

## 12. 향후 확장 계획

### 12.1 단기 확장 (3-6개월)
- 다국어 지원 (영어)
- 방문자 통계 대시보드
- 댓글 기능
- 좋아요/북마크 기능
- RSS 피드

### 12.2 중기 확장 (6-12개월)
- PWA 전환
- 오프라인 모드
- 푸시 알림
- 소셜 공유 최적화
- 프로젝트 타임라인 시각화

### 12.3 장기 확장 (12개월+)
- 포트폴리오 템플릿 시스템
- 다른 개발자와 협업 기능
- 마크다운 기반 블로그 작성
- 코드 샌드박스 통합
- 라이브 코딩 스트림

---

## 13. 부록

### 13.1 용어 정의
- **RAG (Retrieval-Augmented Generation)**: 검색 증강 생성, 외부 지식을 활용하여 LLM의 응답을 개선하는 기술
- **CKEditor**: 리치 텍스트 에디터 라이브러리
- **JWT**: JSON Web Token, 인증 토큰 표준
- **SEO**: Search Engine Optimization, 검색 엔진 최적화
- **PWA**: Progressive Web App, 프로그레시브 웹 앱

### 13.2 참고 자료
- [React 공식 문서](https://react.dev/)
- [CKEditor 5 문서](https://ckeditor.com/docs/ckeditor5/)
- [LangChain 문서](https://js.langchain.com/)
- [WCAG 2.1 가이드라인](https://www.w3.org/WAI/WCAG21/quickref/)
- [Material Design 가이드](https://m3.material.io/)

### 13.3 버전 히스토리
- **v1.0** (2026-02-19): 초기 PRD 작성

---

## 문서 승인

**작성자**: AI Product Manager  
**작성일**: 2026-02-19  
**검토자**: -  
**승인자**: -  
**최종 수정일**: 2026-02-19
