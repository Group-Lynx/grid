declare global {
  type Todo = {
    id: string;
    title: string;
    due: string;
    detail: string;
    done: boolean;
    display: boolean;
  };

  type Mail = {
    id: string;
    title: string;
    detail: string;
    read: boolean;
  };

  type Class = {
    id: string;
    name: string;
  };

  type StuEvent = {
    id: string;
    name: string;
    location: string;
    start: number;
    end: number;
  };

  type StuInfo = {
    id: string;
    name: string;
  };

  type ClassMail = {
    id: string;
    title: string;
    detail: string;
    read: boolean;
    draft: boolean;
  };

  type ClassInfo = {
    // TODO: Define the type of ClassInfo
  };
}

export {};
