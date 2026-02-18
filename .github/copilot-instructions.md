When creating pull request descriptions or reviewing PRs:

## PR Description Template
**What changed**
- Clear summary of modifications and affected components

**Why**
- Business context and requirements
- Technical reasoning for approach taken

**Breaking Changes**
- List any API changes or behavioral modifications
- Include migration instructions if needed

## Review Focus Areas
- **Security**: Check for hardcoded secrets, input validation, auth issues
- **Performance**: Look for database query problems, inefficient loops

## Review Style
- Be specific and constructive in feedback
- Ask clarifying questions when code intent is unclear
- Focus on maintainability and readability improvements
- Always prioritize changes that improve security, performance, or user experience.
- Provide migration guides for significant changes
- Update version compatibility information

## Code Review Guidelines

### Security Review
- Scan for input validation vulnerabilities
- Check authentication and authorization implementation
- Verify secure data handling and storage practices
- Flag hardcoded secrets or configuration issues
- Review error handling to prevent information leakage

### Performance Analysis
- Evaluate algorithmic complexity and efficiency
- Review database query optimization opportunities
- Check for potential memory leaks or resource issues
- Assess caching strategies and network call efficiency
- Identify scalability bottlenecks

### Code Quality Standards
- Ensure readable, maintainable code structure
- Verify adherence to team coding standards and style guides
- Check function size, complexity, and single responsibility
- Review naming conventions and code organization
- Validate proper error handling and logging practices

### Review Communication
- Provide specific, actionable feedback with examples
- Explain reasoning behind recommendations to promote learning
- Ask clarifying questions when context is unclear
- Focus on improvement rather than criticism

## Review Comment Format

Use this structure for consistent, helpful feedback:

**Issue:** Describe what needs attention
**Suggestion:** Provide specific improvement with code example
**Why:** Explain the reasoning and benefits

## Review Labels and Emojis
- 🔒 Security concerns requiring immediate attention
- ⚡ Performance issues or optimization opportunities
- 🧹 Code cleanup and maintainability improvements
- 📚 Documentation gaps or update requirements
- 🚨 Critical issues that block merge
- 💭 Questions for clarification or discussion

Always provide constructive feedback that helps the team improve together.

## ETC
Everything should be written in Korean
