package ru.itpark.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//т.к. мы подключили библиотеку servlet api, он нам дал доступ к httpservlet
//чтобы наш класс стал servlet-ом, он должен отнаследоваться от httpservlet
public class HelloServlet extends HttpServlet {
    //хотим, чтобы наш servlet обрабатывл get запросы

    @Override
    //метод, который будет реагировать на get запросы нашему servlet
    //принимает на вход java объект request, java объект response
    //из request можно данные запроса вытянуть
    //в response можно ответ записать, чтобы дальше servlet отдал ответ
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        writer.write("<h1>Hello!<h1>");
    }
}
