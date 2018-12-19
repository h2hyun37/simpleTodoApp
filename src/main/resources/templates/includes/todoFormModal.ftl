<!-- Modal -->
<div class="modal fade" id="todoFormModal" tabindex="-1" role="dialog" aria-labelledby="todoFormModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="todoFormModalLabel"></h5>
        <button type="button" onclick="todo.clearTodoForm();" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      <input type="hidden" id="inputType" />
      <form name='todoForm' id='todoForm'>
          <input name="todoId" type="hidden" id="todoId" />
          <div><label for="todoContent">todo 내용 : </label><input name="content" type="text" id="todoContent" /></div>
          <div><label for="commaSeparatedReference">참조 ID : </label><input name="commaSeparatedReference" type="text" id="commaSeparatedReference" /></div>
      </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" onclick="todo.clearTodoForm();" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary" onclick="todo.saveTodo(todoId, todoContent, commaSeparatedReference);">Save</button>
      </div>
    </div>
  </div>
</div>