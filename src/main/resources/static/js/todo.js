var todo = {

    _constant : {
        'inputType' : {
            'NEW':'NEW',
            'UPDATE':'UPDATE'
        },
        'status' : {
            'READY':'READY',
            'COMPLETE':'COMPLETE'
        }
    },

    getInputType : function(inputType) {
        return todo._constant.inputType[inputType];
    },

    getCommaSeparatedCheckedTodoIds : function() {
        var commaSeparatedCheckedTodoIdString = '';

        $('input:checkbox[name="todoCheckbox"]').each( function(index) {
            if ($(this).is(':checked')) {
                if (commaSeparatedCheckedTodoIdString !== '') {
                    commaSeparatedCheckedTodoIdString += ',';
                }
                commaSeparatedCheckedTodoIdString += $(this).val().split('_')[1];
            }
        });

        return commaSeparatedCheckedTodoIdString;
    },

    isCheckedOnlyOne : function(commaSeparatedCheckedTodoIdString) {
        if (commaSeparatedCheckedTodoIdString === '') {
            alert('체크된 todo가 없습니다.');
            event.preventDefault();
            return false;
        }
        if (commaSeparatedCheckedTodoIdString.split(',').length > 1) {
            alert('2개 이상의 todo가 체크되었습니다. 수정을 위해 1개의 todo만 체크해주세요.');
            event.preventDefault();
            return false;
        }

        return true;
    },

    openNewTodoModal : function() {
        $('#todoFormModalLabel').html('새로운 todo 등록');

        $('#inputType').val(this.getInputType('NEW'));
        $('#commaSeparatedReference').val(this.getCommaSeparatedCheckedTodoIds());
        $('#commaSeparatedReference').prop('disabled',false);
    },

    openUpdateTodoModal : function() {
        $('#todoFormModalLabel').html('todo 수정');

        var commaSeparatedCheckedTodoIdString = this.getCommaSeparatedCheckedTodoIds();
        if (this.isCheckedOnlyOne(commaSeparatedCheckedTodoIdString) == false) {
            return false;
        }

        var todoId = commaSeparatedCheckedTodoIdString;
        $('#inputType').val(this.getInputType('UPDATE'));
        $('#todoId').val(todoId);
        $('#todoContent').val($('#content_' + todoId).val());
        $('#commaSeparatedReference').val($('#commaSeparatedReference_' + todoId).val());
        $('#commaSeparatedReference').prop('disabled',true);
    },

    clearTodoForm : function() {
        $('#inputType').val('');
        $('#todoId').val('');
        $('#todoContent').val('');
        $('#commaSeparatedReference').val('');
    },

    saveTodo : function(todoId, todoContent, commaSeparatedReference) {
        var inputType = this.getInputType($('#inputType').val());

        if (inputType === todo._constant.inputType.NEW) {
            this.postTodo(todoContent, commaSeparatedReference);

        } else if (inputType === todo._constant.inputType.UPDATE) {
            this.updateTodo(todoId, todoContent);
        }

        this.clearTodoForm();
    },

    getTodo : function(id) {
        $.ajax({
            url: '/api/todos/' + id,
            method: 'GET',
            data: {},
            contentType: 'application/json',
            error: function(xhr, data) {
                alert('API 요청에 실패했습니다 : ' + data);
            },
            success: function(data) {
                if (data.header.code != 'SUCCESS') {
                    alert('error : ' + data.header.code);
                    return;
                }
                return data.data.todo;
            }
        });
    },

    updateTodo : function(id, content) {
        data = {
            'content':content.value
        }

        $.ajax({
            url: '/api/todos/' + id.value,
            method: 'PUT',
            data: JSON.stringify(data),
            contentType: 'application/json',
            error: function(xhr, data) {
                alert('API 요청에 실패했습니다 : ' + data);
            },
            success: function(data) {
                if (data.header.code != 'SUCCESS') {
                    if (data.header.code === 'NOT_FOUND') {
                        alert('존재하지 않는 id 입니다.');
                        return;
                    }
                    alert('error : ' + data.header.code);
                    return;
                }
                alert('수정되었습니다.');
                location.reload();
            }
        });
    },

    updateTodoStatus : function() {
        var todoId = this.getCommaSeparatedCheckedTodoIds();
        if (this.isCheckedOnlyOne(todoId) == false) {
            return ;
        }

        data = {
            'status':todo._constant.status.COMPLETE
        }

        $.ajax({
            url: '/api/todos/' + todoId + '/status',
            method: 'PUT',
            data: JSON.stringify(data),
            contentType: 'application/json',
            error: function(xhr, data) {
                alert('API 요청에 실패했습니다 : ' + data);
            },
            success: function(data) {
                if (data.header.code != 'SUCCESS') {
                    if (data.header.code === 'UNCOMPLETED_TODO_EXISTS') {
                        alert('해당 todo를 참조하는 todo 중에 완료되지 않은 todo가 존재합니다.');
                        return;
                    }
                    alert('error : ' + data.header.code);
                    return;
                }
                alert('상태가 변경되었습니다.');
                location.reload();
            }
        });
    },

    postTodo : function(content, commaSeparatedReference) {
        data = {
            'content':content.value,
            'referenceIdList':JSON.parse("[" + commaSeparatedReference.value + "]")
        }

        $.ajax({
            url: '/api/todos',
            method: 'POST',
            data: JSON.stringify(data),
            contentType: 'application/json',
            error: function(xhr, data) {
                alert('API 요청에 실패했습니다 : ' + data);
            },
            success: function(data) {
                if (data.header.code != 'SUCCESS') {
                    if (data.header.code === 'NOT_FOUND') {
                        alert('존재하지 않는 todo를 참조합니다.');
                        return;
                    }
                    alert('error : ' + data.header.code);
                    return;
                }

                alert('등록되었습니다.');
                location.reload();
            }
        });
    }


}