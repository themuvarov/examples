import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by uvarov.m on 16.01.2015.
 */
public class ImagesFilenameFilter implements FilenameFilter {

    private static class YoungestFile {
        public String fileName;
        public long fileTimestamp;
        YoungestFile (String name, long timestamp) {
            fileName = name;
            fileTimestamp = timestamp;
        }
    }

    private Pattern MY_PATTERN = Pattern.compile("^stream_([\\d]{13})\\.jpg$");
    private long searchIntervalSec;
    private YoungestFile young;

    public ImagesFilenameFilter(long searchIntervalSec) {
        this.searchIntervalSec = searchIntervalSec;
    }


    public String getTheYoungestFileName() {
        if (young == null) {
            return "";
        }
        return young.fileName;
    }

    @Override
    public boolean accept(File dir, String name) {
        String currentTime =  String.valueOf(System.currentTimeMillis());
        Matcher m = MY_PATTERN.matcher(name);
        if (m.find()) {
            Long ts = Long.parseLong(m.group(1));
            if(isFitInterval(ts)) {
                updateYoungest(new YoungestFile(name, ts));
                return true;
            }
        }
        return false;
    }

    private void updateYoungest(YoungestFile file) {
        if(young == null) {
            young = file;
            return;
        }

        if(young.fileTimestamp > file.fileTimestamp) {
            young = file;
        }
    }

    private boolean isFitInterval(long fileTimestamp) {
        if(System.currentTimeMillis() - searchIntervalSec * 1000 > fileTimestamp) {
            return false;
        }
        return true;
    }

}
