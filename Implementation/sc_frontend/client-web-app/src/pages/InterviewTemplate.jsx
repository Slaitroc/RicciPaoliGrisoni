import { Outlet } from "react-router-dom";
import { InterviewTemplateProvider } from "../components/InterviewTemplate/InterviewTemplateContext";
const InterviewTemplate = () => {
  return (
    <InterviewTemplateProvider>
      <Outlet />
    </InterviewTemplateProvider>
  );
};

export default InterviewTemplate;
