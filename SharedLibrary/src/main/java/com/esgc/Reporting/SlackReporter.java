package com.esgc.Reporting;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class SlackReporter {

    private static String urlSlackWebHook = "https://hooks.slack.com/services/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";
    private static String channelName = "#platform-ui-smoke-test-execution";
    private static String botUserOAuthAccessToken = "YOUR_BOT_USER_OAuth_TOKEN";

    public void sendTestExecutionStatusToSlack(String message) throws Exception {
        try {

          /*  Payload payload = Payload.builder()
                    .channel("#random")
                    .text("Hello World!")
                    .iconEmoji(":smile_cat:")
                    .username("jSlack")
                    .attachments(new ArrayList<>())
                    .build();

            Attachment attachment = Attachment.builder()
                    .text("This is an *attachment*.")
                    .authorName("Smiling Imp")
                    .color("#36a64f")
                    .fallback("Required plain-text summary of the attachment.")
                    .title("Slack API Documentation")
                    .titleLink("https://api.slack.com/")
                    .footer("footer")
                    .fields(new ArrayList<>())
                    .mrkdwnIn(new ArrayList<>())
                    .build();

            attachment.getMrkdwnIn().add("text");

            {
                Field field = Field.builder()
                        .title("Long Title")
                        .value("Long Value........................................................")
                        .valueShortEnough(false).build();
                attachment.getFields().add(field);
                attachment.getFields().add(field);
            }
            {
                Field field = Field.builder()
                        .title("Short Title")
                        .value("Short Value")
                        .valueShortEnough(true).build();
                attachment.getFields().add(field);
                attachment.getFields().add(field);
            }
            payload.getAttachments().add(attachment);

            WebhookResponse response = slack.send(url, payload);
            log.info(response.toString());
        }

*/




        StringBuilder messageBuider = new StringBuilder();
            messageBuider.append(message);
            Payload payload = Payload.builder().channel(channelName).text(messageBuider.toString()).build();

            WebhookResponse webhookResponse = Slack.getInstance().send(urlSlackWebHook, payload);
            System.out.println("webhookResponse.getMessage() = " + webhookResponse.toString());
        } catch (IOException e) {
            System.out.println("Unexpected Error! WebHook:" + urlSlackWebHook);
        }
    }

    public void sendTestExecutionReportToSlack(String testReportPath) throws Exception {
        String url = "https://moodys-ma-integration.slack.com/api/files.upload";
        try {
            /*Response response = given().log().all()
                    .baseUri(url)
                    .relaxedHTTPSValidation().accept(ContentType.JSON);
                    .contentType("application/json")
                    .body(payload2)
                    .when()
                    .post("raven/1.0/import/execution").prettyPeek();*/


            HttpClient httpclient = HttpClientBuilder.create().disableContentCompression().build();
            HttpPost httppost = new HttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            FileBody fileBody = new FileBody(new File(testReportPath));
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.addPart("file", fileBody);
            builder.addTextBody("channels", channelName);
//            builder.addTextBody("token", botUserOAuthAccessToken);
            httppost.setEntity(builder.build());
            HttpResponse response = null;
            response = httpclient.execute(httppost);
            HttpEntity result = response.getEntity();
            System.out.println(response.getStatusLine());
            System.out.println(response);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(description = "demo")
    public void sendSlack() {
        try {

             String message = "Smoke Test Execution Results\n" +
                    ":computer: Environment: QA\n" +
                    ":clipboard: Total Test Cases: 644\n" +
                    ":white_Check_mark: Passed Test Cases: 625\n" +
                    ":x: Failed Test Cases: 19\n" +
                    ":chart_with_upwards_trend:Pass rate: 97%\n" +
                    "<https://esjira/browse/ESGCA-6985|View results in Jira>\n";

            sendTestExecutionStatusToSlack(message);
            //sendTestExecutionReportToSlack("test-output/Smoke.html");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    les.upload
Java
JavaScript
Python
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.jetty.SlackAppServer;
import com.slack.api.methods.SlackApiException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class FilesUpload {

    public static void main(String[] args) throws Exception {
        var config = new AppConfig();
        config.setSingleTeamBotToken(System.getenv("SLACK_BOT_TOKEN"));
        config.setSigningSecret(System.getenv("SLACK_SIGNING_SECRET"));
        var app = new App(config); // `new App()` does the same

        app.command("/testUpload", (req, ctx) -> {
            var logger = ctx.logger;
            try {
                var payload = req.getPayload();
                // The name of the file you're going to upload
                var filepath = "./myFileName.gif";
                // Call the files.upload method using the built-in WebClient
                var result = ctx.client().filesUpload(r -> r
                    // The token you used to initialize your app is stored in the `context` object
                    .token(ctx.getBotToken())
                    .channels(Arrays.asList(payload.getChannelId()))
                    .initialComment("Here's my file :smile:")
                    // Include your filename in a ReadStream here
                    .file(new File(filepath))
                );
                // Print result
                logger.info("result: {}", result);
            } catch (IOException | SlackApiException e) {
                logger.error("error: {}", e.getMessage(), e);
            }
            // Acknowledge incoming command event
            return ctx.ack();
        });

        var server = new SlackAppServer(app);
        server.start();
    }

}
     */
}
