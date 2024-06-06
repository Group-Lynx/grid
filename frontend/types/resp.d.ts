declare global {
  type StuInfoResp = {
    studentId: string;
    studentName: string;
  };
  type StuClaResp = {
    classId: string;
    className: string;
  };
  type StuMailResp = {
    mailId: string;
    title: string;
  };
  type StuMailDetailResp = {
    title: string;
    detail: string;
  };
  type StuTodoResp = {
    todoId: string;
    title: string;
    due: Date;
    detail: string;
    done: boolean;
    display: boolean;
  };
  type StuEventResp = {
    id: string;
    name: string;
    location: string;
    start_hour: number;
    end_hour: number;
    date: Date;
  };
  type StuTodoReq = {
    title?: string;
    due?: Date;
    detail?: string;
    done?: boolean;
    display?: boolean;
  };
}

export {};
