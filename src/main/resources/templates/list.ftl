<!doctype html>
<html lang="ko">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap -->
    <link href="/static/css/bootstrap-4.1.3/bootstrap.min.css" rel="stylesheet">
    <link href="/static/css/todo.css" rel="stylesheet">

    <!-- jquery -->
    <script src="/static/js/jquery-3.3.1/jquery-3.3.1.js"></script>

    <script src="/static/js/todo.js"></script>

    <title>Simple Todo</title>
  </head>
  <body>
  <div class="container">

    <!-- Content here -->
    <table class="table table-hover">
      <thead>
        <tr>
          <th scope="col"></th>
          <th scope="col">id</th>
          <th scope="col">할일</th>
          <th scope="col">작성일시</th>
          <th scope="col">최종수정일시</th>
          <th scope="col">상태</th>
        </tr>
      </thead>
      <tbody>
      <form id='todoListForm'>
      <#list todoList as todo>
        <#assign content = todo.content>
        <#assign commaSeparatedReferenceId = "">

        <#list todo.referenceList as reference>
            <#if reference.referenceId??>
                <#assign content = content + " @" + reference.referenceId>

                <#if commaSeparatedReferenceId == "">
                    <#assign commaSeparatedReferenceId = reference.referenceId + "">
                <#else>
                    <#assign commaSeparatedReferenceId = commaSeparatedReferenceId + "," + reference.referenceId>
                </#if>

            </#if>
        </#list>

        <tr <#if todo.status! == "COMPLETE">class="table-secondary"</#if>>
          <td>
            <input type="checkbox" name="todoCheckbox" value="todoCheckbox_${todo.todoId}" />
            <input type="hidden" id="content_${todo.todoId}" value="${todo.content}" />
            <input type="hidden" id="commaSeparatedReference_${todo.todoId}" value="${commaSeparatedReferenceId!}" />
          </td>
          <td>${todo.todoId}</td>
          <td>${content}</td>
          <td>${todo.createdYmdt}</td>
          <td>${todo.lastModifiedYmdt}</td>
          <td>
            <#if todo.status! == "READY">진행중
            <#elseif todo.status! == "COMPLETE">완료
            </#if>
          </td>
        </tr>

      </#list>
      </form>
      </tbody>
    </table>
    <div>

    <#include "./includes/pagination.ftl">
    </div>

    <div>
    <button type="button" class="btn btn-primary" onclick="todo.openNewTodoModal();" data-toggle="modal" data-target="#todoFormModal">새 할일 등록</button>
    <button type="button" class="btn btn-primary" onclick="return todo.openUpdateTodoModal();" data-toggle="modal" data-target="#todoFormModal">수정</button>
    <button type="button" class="btn btn-outline-secondary" onclick="todo.updateTodoStatus();">완료처리</button>
    </div>
    <#include "./includes/todoFormModal.ftl">
  </div>

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
  </body>
</html>