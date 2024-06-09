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
    eventId: string;
    name: string;
    location: string;
    start: number;
    end: number;
    date: Date;
  };
  type StuTodoReq = {
    title?: string;
    due?: Date;
    detail?: string;
    done?: boolean;
    display?: boolean;
  };
  type TchClaResp = {
    classId: string;
    className: string;
  };
  type ClaInfoResp = {
    classId: string;
    className: string;
  };
  type ClaStuResp = {
    studentId: string;
    studentName: string;
  };
  type ClaMailResp = {
    mailId: string;
    title: string;
  };
  type MailDetailResp = {
    title: string;
    detail: string;
    draft: boolean;
  };
  type TchInfoResp = {
    teacherId: string;
    teacherName: string;
  };
  type mailPatchReq = {
    title?: string;
    detail?: string;
    draft?: boolean;
  };
}

export {};
