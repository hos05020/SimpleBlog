package com.example.BackendTask.service

import com.example.BackendTask.domain.Article
import com.example.BackendTask.domain.User
import com.example.BackendTask.exception.BlogException
import com.example.BackendTask.repository.ArticleRepository
import com.example.BackendTask.repository.UserRepository
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
internal class ArticleServiceTest{

    @Autowired
    protected lateinit var articleService : ArticleService

    @Autowired
    protected lateinit var articleRepository: ArticleRepository

    @Autowired
    protected lateinit var userRepository: UserRepository


    @DisplayName("글 작성 성공")
    @Test
    fun test1(){
        val user = makeUser()
        val response = articleService.register(user, "제목1", "내용1")
        assertThat(response.title).isEqualTo("제목1")
        assertThat(response.content).isEqualTo("내용1")
        assertThat(response.email).isEqualTo(user.email)
        assertThat(articleRepository.findByIdOrNull(response.articleId)).isNotNull

    }


    @DisplayName("글 작성자가 아닌 경우 수정불가")
    @Test
    fun test2(){
        val user = makeUser()
        val article = Article(
                title = "제목1",
                content = "내용1",
                user = user
        )

        articleRepository.save(article)

        val user2 = User(
                email = "이메일2",
                password = "비밀번호2",
                username = "유저2"
        )

        userRepository.save(user2)

        assertThatThrownBy { article.id?.let { articleService.modify(it,user2,"제목1-수정","내용1-수정","/article/"+article.id) } }
                .isInstanceOf(BlogException::class.java)
                .hasMessageContaining("접근 권한이 없습니다")


    }

    @DisplayName("글 수정 성공")
    @Test
    fun test3(){
        val user = makeUser()
        val article = Article(
                title = "제목1",
                content = "내용1",
                user = user
        )

        articleRepository.save(article)

        val response = article.id?.let {
            articleService.modify(it, user, "제목1-수정", "내용1-수정", "/article/${article.id}")
        }

        assertThat(response!!.articleId).isEqualTo(article.id)
        assertThat(response!!.title).isEqualTo("제목1-수정")
        assertThat(response!!.content).isEqualTo("내용1-수정")
        assertThat(response!!.email).isEqualTo(user.email)


    }

    @DisplayName("글 작성자가 아닌 경우 삭제불가")
    @Test
    fun test4(){
        val user = makeUser()
        val article = Article(
                title = "제목1",
                content = "내용1",
                user = user
        )

        articleRepository.save(article)

        val user2 = User(
                email = "이메일2",
                password = "비밀번호2",
                username = "유저2"
        )

        userRepository.save(user2)

        assertThatThrownBy { article.id?.let { articleService.remove(it,user2,"/article/"+article.id) } }
                .isInstanceOf(BlogException::class.java)
                .hasMessageContaining("접근 권한이 없습니다")


    }



    @DisplayName("글 삭제 성공")
    @Test
    fun test5(){
        val user = makeUser()
        val article = Article(
                title = "제목1",
                content = "내용1",
                user = user
        )

        articleRepository.save(article)

        val response = article.id?.let {
            articleService.remove(it, user, "/article/${article.id}")
        }

        assertThat(articleRepository.findByIdOrNull(article.id)).isNull()


    }


    @DisplayName("없는 글에 접근 불가")
    @Test
    fun test6(){
        val user = makeUser()
        val article = Article(
                title = "제목1",
                content = "내용1",
                user = user
        )

        articleRepository.save(article)


        assertThatThrownBy { articleService.modify(100L,user,"제목1-수정","내용1-수정","/articles/100") }
                .isInstanceOf(BlogException::class.java)
                .hasMessageContaining("게시글을 찾을 수 없습니다.")


    }



    @DisplayName("멤버 탈퇴시 글은 자동 삭제")
    @Test
    fun test7(){
        val user = makeUser()
        val response = articleService.register(user, "제목1", "내용1")


        userRepository.delete(user)


        assertThat(articleRepository.findByIdOrNull(response.articleId)).isNull()


    }


    fun makeUser():User{
        val user = User(
                email = "이메일1",
                password = "비밀번호1",
                username = "유저1"
        )

        userRepository.save(user);
        return user
    }



}