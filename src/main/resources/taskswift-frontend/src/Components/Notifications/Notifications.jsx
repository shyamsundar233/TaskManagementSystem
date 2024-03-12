import "./Notifications.css";
import pinIcon from "../../Assets/pin-tack.svg";
import updateIcon from  "../../Assets/notification.svg";
import NotifyCard from "../NotifyCard/NotifyCard";

const Notifications = () => {
    return (
        <div className="notf-parent-cont-1">
            <div className="notf-parent-cont-2 scroll-div">
                <div className="notf-body-1">
                    <div className="title-font notf-title-cont">
                        <img src={pinIcon} alt="Pin Icon is missing"/>&nbsp;
                        Pinned
                    </div>
                    <div className="notf-card-cont">
                        <NotifyCard user="Emma" status="replied to you" title="Urgent: Finalize the Budget Proposal" body="Please review and finalize the budget proposal before end of day."/>
                        <NotifyCard user="John" status="replied to you" title="Important: Project Status Meeting" body="Project status meeting scheduled for tomorrow. Prepare accordingly."/>
                        <NotifyCard user="Sophia" status="replied to you" title="Reminder: Submit Weekly Progress Reports" body="Don't forget to submit your weekly progress reports by today's deadline."/>
                        <NotifyCard user="Michael" status="replied to you" title="Task Deadline Approaching" body="Ensure completion of assigned tasks by today as discussed."/>
                        <NotifyCard user="Olivia" status="replied to you" title="Feedback Required: Marketing Strategy Draft" body="Awaiting your feedback on the marketing strategy draft. Please review at your earliest convenience."/>

                    </div>
                </div>
                <div className="notf-body-1">
                    <div className="title-font notf-title-cont">
                        <img src={updateIcon} alt="Pin Icon is missing"/>&nbsp;
                        Task Updates
                    </div>
                    <div className="notf-card-cont">
                        <NotifyCard user="Sophie" status="replied to you" title="Urgent: Client Presentation" body="Finalize the slides and incorporate client feedback before end of day."/>
                        <NotifyCard user="Liam" status="replied to you" title="Important: Team Meeting Agenda" body="Review the team meeting agenda and suggest any additional discussion points."/>
                        <NotifyCard user="Ava" status="replied to you" title="Reminder: Submit Monthly Reports" body="Submit your monthly reports by today's deadline to ensure timely processing."/>
                        <NotifyCard user="Noah" status="replied to you" title="Task Deadline Reminder" body="Double-check the completion status of all assigned tasks and provide updates as necessary."/>
                        <NotifyCard user="Isabella" status="replied to you" title="Feedback Needed: Product Prototype" body="Examine the latest product prototype and provide detailed feedback on its functionality and design."/>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Notifications;