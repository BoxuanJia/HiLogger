package com.github.boxuanjia.hilogger;

import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;
import ohos.eventhandler.InnerEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.github.boxuanjia.hilogger.Utils.checkNotNull;

/**
 * Abstract class that takes care of background threading the file log operation on Harmony.
 * implementing classes are free to directly perform I/O operations there.
 * <p>
 * Writes all logs to the disk with CSV format.
 */
public class DiskLogStrategy implements LogStrategy {

    private final EventHandler handler;

    public DiskLogStrategy(EventHandler handler) {
        this.handler = checkNotNull(handler);
    }

    @Override
    public void log(int level, int domain, String tag, String message) {
        checkNotNull(message);

        // do nothing on the calling thread, simply pass the tag/msg to the background thread
        handler.sendEvent(InnerEvent.get(level, message));
    }

    static class WriteHandler extends EventHandler {

        private final String folder;
        private final int maxFileSize;

        WriteHandler(EventRunner looper, String folder, int maxFileSize) {
            super(checkNotNull(looper));
            this.folder = checkNotNull(folder);
            this.maxFileSize = maxFileSize;
        }

        @Override
        protected void processEvent(InnerEvent event) {
            super.processEvent(event);
            String content = (String) event.object;

            FileWriter fileWriter = null;
            File logFile = getLogFile(folder, "logs");

            try {
                fileWriter = new FileWriter(logFile, true);

                writeLog(fileWriter, content);

                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                if (fileWriter != null) {
                    try {
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (IOException e1) { /* fail silently */ }
                }
            }
        }

        /**
         * This is always called on a single background thread.
         * Implementing classes must ONLY write to the fileWriter and nothing more.
         * The abstract class takes care of everything else including close the stream and catching IOException
         *
         * @param fileWriter an instance of FileWriter already initialised to the correct file
         */
        private void writeLog(FileWriter fileWriter, String content) throws IOException {
            checkNotNull(fileWriter);
            checkNotNull(content);

            fileWriter.append(content);
        }

        private File getLogFile(String folderName, String fileName) {
            checkNotNull(folderName);
            checkNotNull(fileName);

            File folder = new File(folderName);
            if (!folder.exists()) {
                //TODO: What if folder is not created, what happens then?
                folder.mkdirs();
            }

            int newFileCount = 0;
            File newFile;
            File existingFile = null;

            newFile = new File(folder, String.format("%s_%s.csv", fileName, newFileCount));
            while (newFile.exists()) {
                existingFile = newFile;
                newFileCount++;
                newFile = new File(folder, String.format("%s_%s.csv", fileName, newFileCount));
            }

            if (existingFile != null) {
                if (existingFile.length() >= maxFileSize) {
                    return newFile;
                }
                return existingFile;
            }

            return newFile;
        }
    }
}
