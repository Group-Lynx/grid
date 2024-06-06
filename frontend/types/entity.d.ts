declare global {
  type Todo = {
    id: string;
    title: string;
    due: Date;
    detail: string;
    done: boolean;
    display: boolean;
  };

  type Mail = {
    id: string;
    title: string;
    detail: string?;
  };

  type Class = {
    id: string;
    name: string;
  };

  type StuEvent = {
    id: string;
    name: string;
    location: string;
    date: Date;
    start: number;
    end: number;
  };

  type StuInfo = {
    id: string;
    name: string;
  };

  type TchInfo = {
    id: string;
    name: string;
  };

  type ClassMail = {
    id: string;
    title: string;
    detail: string;
    draft: boolean;
  };

  type ClassInfo = {
    id: string;
    name: string;
  };
}

export {};
